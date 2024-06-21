package br.edu.infnet.at_marianabs.service;

import br.edu.infnet.at_marianabs.execption.ResourceNotFoundException;
import br.edu.infnet.at_marianabs.model.Departamento;
import br.edu.infnet.at_marianabs.model.Funcionario;
import br.edu.infnet.at_marianabs.repository.FuncionarioRepository;
import br.edu.infnet.at_marianabs.repository.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private DepartamentoRepository departamentoRepository;

    public List<Funcionario> listar() {
        return funcionarioRepository.findAll();
    }

    public Optional<Funcionario> buscarPorId(Integer id) {
        return funcionarioRepository.findById(id);
    }

    public Funcionario salvar(Funcionario funcionario) {
        Integer id =funcionario.getDepartamento().getId();
        Optional<Departamento> departamento = departamentoRepository.findById(id);
        departamento.ifPresent(funcionario::setDepartamento);
        return funcionarioRepository.save(funcionario);
    }

    public Funcionario atualizar(Integer id, Funcionario funcionario) {
        if (funcionarioRepository.existsById(id)) {
            Optional<Departamento> departamento = departamentoRepository.findById(funcionario.getDepartamento().getId());
            departamento.ifPresent(funcionario::setDepartamento);
            funcionario.setId(id);
            return funcionarioRepository.save(funcionario);
        }
        throw new ResourceNotFoundException("Funcionario não encontrado com o id: "+id);
    }

    public void remover(Integer id) {
        if (funcionarioRepository.existsById(id)) {
            funcionarioRepository.deleteById(id);
            return;
        }
        throw new ResourceNotFoundException("Funcionario não encontrado com o id: "+id);
    }
}
