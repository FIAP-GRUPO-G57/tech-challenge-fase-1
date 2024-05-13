package br.com.fiap.lanchonete.infrastructure.produto.dto;

import br.com.fiap.lanchonete.entity.produto.model.Categoria;
import br.com.fiap.lanchonete.usecase.produto.dto.IProdutoRegistrationData;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record ProdutoRegistrationData(
	Long id,
	@NotNull String nome,
	@NotNull Categoria categoria,
	BigDecimal preco,
	String descricao,
	List<String> imagens
) implements IProdutoRegistrationData {}
