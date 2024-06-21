package br.edu.infnet.at_marianabs.repository;

import br.edu.infnet.at_marianabs.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
}
