package br.com.fiap.lanchonete.usecase.produto.dto;

import br.com.fiap.lanchonete.entity.produto.model.Categoria;

import java.math.BigDecimal;
import java.util.List;

public interface IProdutoRegistrationData {
	Long id();
	String nome();
	Categoria categoria();
	BigDecimal preco();
	String descricao();
	List<String> imagens();
}
