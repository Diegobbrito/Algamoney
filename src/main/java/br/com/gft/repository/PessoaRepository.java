package br.com.gft.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gft.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
 