 package br.com.gft.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gft.model.Lancamento;
import br.com.gft.model.Pessoa;
import br.com.gft.repository.LancamentoRepository;
import br.com.gft.repository.PessoaRepository;
import br.com.gft.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;


	public Lancamento salvar(@Valid Lancamento lancamento) {
		Pessoa pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo()).isPresent() ? pessoaRepository.findById(lancamento.getPessoa().getCodigo()).get() : null;
		if(pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		
		
		return lancamentoRepository.save(lancamento);
	}

}
