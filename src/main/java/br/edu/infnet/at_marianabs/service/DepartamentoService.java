package br.edu.infnet.at_marianabs.service;

import br.edu.infnet.at_marianabs.execption.ResourceNotFoundException;
import br.edu.infnet.at_marianabs.model.Departamento;
import br.edu.infnet.at_marianabs.repository.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoService {
    @Autowired
    private DepartamentoRepository departamentoRepository;

    public List<Departamento> listar() {
        return departamentoRepository.findAll();
    }

    public Optional<Departamento> buscarPorId(Integer id) {
        return departamentoRepository.findById(id);
    }

    public Departamento salvar(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }

    public Departamento atualizar(Integer id, Departamento departamento) {
        if (departamentoRepository.existsById(id)) {
            departamento.setId(id);
            return departamentoRepository.save(departamento);
        }
        throw new ResourceNotFoundException("Departamento não encontrado com o id: "+id);
    }

    public void remover(Integer id) {
        if (departamentoRepository.existsById(id)) {
            departamentoRepository.deleteById(id);
            return;
        }
        throw new ResourceNotFoundException("Departamento não encontrado com o id: "+id);
    }
}
