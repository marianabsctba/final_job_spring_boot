package br.edu.infnet.at_marianabs.controller;

import br.edu.infnet.at_marianabs.model.Departamento;
import br.edu.infnet.at_marianabs.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/public/departamentos")
public class DepartamentoController {

    @Autowired
    private DepartamentoService departamentoService;

    @GetMapping
    public ResponseEntity<List<Departamento>> listarDepartamentos() {
        return ResponseEntity.ok(departamentoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Departamento> buscarPorId(@PathVariable Integer id) {
        Optional<Departamento> departamento = departamentoService.buscarPorId(id);
        if (departamento.isPresent()) {
            return ResponseEntity.ok().body(departamento.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Departamento> salvarDepartamento(@RequestBody Departamento departamento) {
        Departamento departamentoSaved = departamentoService.salvar(departamento);
        return new ResponseEntity<>(departamentoSaved,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Departamento> atualizarDepartamento(@PathVariable Integer id, @RequestBody Departamento departamento) {
        return ResponseEntity.ok().body(departamentoService.atualizar(id, departamento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerDepartamento(@PathVariable Integer id) {
        departamentoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
