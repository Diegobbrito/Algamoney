package br.com.algamoney.model;

import javax.persistence.Embeddable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class Endereco {
	
	@ApiModelProperty(example = "Rua do Sabiá")
	private String logradouro;
	
	@ApiModelProperty(example = "11")
	private String numero;
	
	@ApiModelProperty(example = "Apto 12")
	private String complemento;
	
	@ApiModelProperty(example = "Colina")
	private String bairro;
	
	@ApiModelProperty(example = "11.400-12")
	private String cep;
	
	@ApiModelProperty(example = "Ribeirão Preto")
	private String cidade;
	
	@ApiModelProperty(example = "SP")
	private String estado;	
}
 