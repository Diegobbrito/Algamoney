package br.com.gft.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gft.model.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
 