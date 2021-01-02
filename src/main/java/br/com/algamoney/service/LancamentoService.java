 package br.com.algamoney.service;

import javax.validation.Valid;

import br.com.algamoney.repository.LancamentoRepository;
import br.com.algamoney.repository.PessoaRepository;
import br.com.algamoney.service.exception.PessoaInexistenteOuInativaException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.stereotype.Service;

import br.com.algamoney.model.Lancamento;
import br.com.algamoney.model.Pessoa;

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

	public Lancamento atualizar(Long codigo, Lancamento lancamento){
		Lancamento lancamentoSalvo = buscarLancamentoExistente(codigo);

		if(!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())){
			validarPessoa(lancamento);
		}
		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");

		return  lancamentoRepository.save(lancamentoSalvo);
	}

	 private Lancamento buscarLancamentoExistente(Long codigo) {
		Lancamento lancamentoSalvo = lancamentoRepository.getOne(codigo);

		if (lancamentoSalvo == null){
			throw new IllegalArgumentException();
		}
		return lancamentoSalvo;
	 }

	 private void validarPessoa(Lancamento lancamento) {
		Pessoa pessoa = null;

		if(lancamento.getPessoa().getCodigo() != null){
			pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo()).get();
		}

		if (pessoa == null || pessoa.isInativo()){
			throw new PessoaInexistenteOuInativaException();
		}
	 }


 }
