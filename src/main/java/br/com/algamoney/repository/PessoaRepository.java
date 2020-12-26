package br.com.algamoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.algamoney.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
 