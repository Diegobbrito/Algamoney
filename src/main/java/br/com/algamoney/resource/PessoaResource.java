package br.com.algamoney.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import br.com.algamoney.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.algamoney.event.RecursoCriadoEvent;
import br.com.algamoney.model.Pessoa;
import br.com.algamoney.service.PessoaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Pessoas")
@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@ApiOperation("Lista todas as pessoas")
	@GetMapping
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public List<Pessoa> listar() {
		return pessoaRepository.findAll();
	}

	@ApiOperation("Buscar uma pessoa pelo codigo")
	@GetMapping("/{codigo}")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> buscarPeloCodigo(
			@ApiParam(value = "Codigo de uma pessoa", example = "1") @PathVariable Long codigo) {
		Pessoa pessoa = pessoaRepository.findById(codigo).isPresent() ? pessoaRepository.findById(codigo).get() : null;
		return pessoa == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(pessoa);
	}

	@ApiOperation("Criar uma nova pessoa")
	@PostMapping
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Pessoa> criar(
			@ApiParam(name = "corpo", value = "Representação de uma nova pessoa") @Valid @RequestBody Pessoa pessoa,
			HttpServletResponse response) {
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}

	@ApiOperation("Atualizar pessoa pelo codigo")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> atualizar(
			@ApiParam(value = "Codigo de uma pessoa", example = "1") @PathVariable Long codigo,
			@ApiParam(name = "corpo", value = "Representação de uma pessoa com novos dados") @Valid @RequestBody Pessoa pessoa) {

		Pessoa pessoaSalva = pessoaService.atualizar(codigo, pessoa);

		return ResponseEntity.ok(pessoaSalva);
	}

	@ApiOperation("Atualizar status da pessoa pelo codigo")
	@PutMapping("/{codigo}/ativo")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropiedadeAtivo(
			@ApiParam(value = "Codigo de uma pessoa", example = "1") @PathVariable Long codigo,
			@ApiParam(value = "Status de uma pessoa", example = "true") @RequestBody Boolean ativo) {

		pessoaService.atualizarPropiedadeAtivo(codigo, ativo);

	}

	@ApiOperation("Deletar pessoa pelo codigo")
	@DeleteMapping("/{codigo}")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@ApiParam(value = "Codigo de uma pessoa", example = "1") @PathVariable Long codigo) {
		pessoaRepository.deleteById(codigo);
	}

}
