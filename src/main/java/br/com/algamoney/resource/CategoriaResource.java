package br.com.algamoney.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import br.com.algamoney.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.algamoney.event.RecursoCriadoEvent;
import br.com.algamoney.model.Categoria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Categorias")
@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@ApiOperation("Lista todas as categorias")			//Utilizado na documentação
	@GetMapping
	public List<Categoria> listar() {
		return categoriaRepository.findAll();
	}

	@ApiOperation("Cria uma nova categoria")
	@PostMapping										//Utilizado para utilizar token na documentação
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Categoria> criar(			//Utilizado na documentação
			@ApiParam(name = "corpo", value = "Representação de uma nova categoria") @Valid @RequestBody Categoria categoria,
			HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);

	}

	@ApiOperation("Busca uma categoria pelo codigo")
	@GetMapping("/{codigo}")
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	public ResponseEntity<?> buscarPeloCodigo(
			@ApiParam(value = "Codigo de uma categoria", example = "1") @PathVariable Long codigo) {
		Optional<Categoria> categoria = categoriaRepository.findById(codigo);
		return categoria.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(categoria);
	}

}
