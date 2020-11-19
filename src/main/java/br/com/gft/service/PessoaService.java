package br.com.gft.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.gft.model.Pessoa;
import br.com.gft.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);

		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");

		return pessoaRepository.save(pessoaSalva);
	}

	public void atualizarPropiedadeAtivo(Long codigo, Boolean ativo) {

		Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
		
		pessoaSalva.setAtivo(ativo);
		
		pessoaRepository.save(pessoaSalva);
	}

	public Pessoa buscarPessoaPeloCodigo(Long codigo) {
		Pessoa pessoaSalva = pessoaRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));

		return pessoaSalva;
	}

}
