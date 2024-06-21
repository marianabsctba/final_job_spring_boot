package br.edu.infnet.at_marianabs.controller;

import br.edu.infnet.at_marianabs.model.Usuario;
import br.edu.infnet.at_marianabs.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/api/private/usuarios")
    public String rotaPrivada (){
        return "Olá mundo! Respondendo da rota privada.";
    }

    @GetMapping("/api/public/usuarios")
    public ResponseEntity<List<Usuario>> listarFuncionarios() {
       return ResponseEntity.ok(usuarioService.listar());
    }

    @GetMapping("/api/public/usuarios/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable String id) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok().body(usuario.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/public/usuarios")
    public ResponseEntity<Usuario> salvarUsuario(@RequestBody Usuario usuario) {
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        return new ResponseEntity<>(usuarioService.salvar(usuario), HttpStatus.CREATED);
    }

    @PutMapping("/api/public/usuarios/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable String id, @RequestBody Usuario usuario) {
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        return ResponseEntity.ok().body(usuarioService.atualizar(id, usuario));
    }

    @DeleteMapping("/api/public/usuarios/{id}")
    public ResponseEntity<Void> removerUsuario(@PathVariable String id) {
        usuarioService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
