package br.edu.infnet.at_marianabs.controller;

import br.edu.infnet.at_marianabs.model.Departamento;
import br.edu.infnet.at_marianabs.model.Funcionario;
import br.edu.infnet.at_marianabs.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/public/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public ResponseEntity<List<Funcionario>> listarFuncionarios() {
        return ResponseEntity.ok(funcionarioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarPorId(@PathVariable Integer id) {
        Optional<Funcionario> funcionario = funcionarioService.buscarPorId(id);
        if (funcionario.isPresent()) {
            return ResponseEntity.ok().body(funcionario.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Funcionario> salvarFuncionario(@RequestBody Funcionario funcionario) {
        Funcionario funcionarioSaved = funcionarioService.salvar(funcionario);
        return new ResponseEntity<>(funcionarioSaved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizarFuncionario(@PathVariable Integer id, @RequestBody Funcionario funcionario) {
        return ResponseEntity.ok().body(funcionarioService.atualizar(id, funcionario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerFuncionario(@PathVariable Integer id) {
        funcionarioService.remover(id);
        return ResponseEntity.noContent().build();
    }
}

