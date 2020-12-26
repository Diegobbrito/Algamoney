package br.com.algamoney.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lancamento")
@Getter @Setter
public class Lancamento {

	@ApiModelProperty(value = "Codigo do lancamento",example = "1")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@ApiModelProperty(value = "Descrição do lancamento", example = "Salário mensal")
	@NotNull
	private String descricao;
	
	
	@ApiModelProperty( example = "30/10/2020")
	@NotNull
	@Column(name = "data_vencimento")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataVencimento;
	
	@ApiModelProperty( example = "29/10/2020")
	@Column(name = "data_pagamento")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataPagamento;
	
	@ApiModelProperty(example = "6500.00")
	@NotNull
	private BigDecimal valor;
	
	@ApiModelProperty(value = "Distribuição de lucros")
	private String observacao;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoLancamento tipo;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "codigo_categoria")
	private Categoria categoria;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "codigo_pessoa")
	private Pessoa pessoa;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lancamento other = (Lancamento) obj;
		if (codigo == null) {
			return other.codigo == null;
		} else return codigo.equals(other.codigo);
	}
}
