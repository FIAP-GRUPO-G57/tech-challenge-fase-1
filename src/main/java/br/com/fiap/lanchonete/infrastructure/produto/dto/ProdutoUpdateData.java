package br.com.fiap.lanchonete.infrastructure.produto.dto;

import br.com.fiap.lanchonete.entity.produto.model.Categoria;
import br.com.fiap.lanchonete.usecase.produto.dto.IProdutoRegistrationData;
import br.com.fiap.lanchonete.usecase.produto.dto.IProdutoUpdateData;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record ProdutoUpdateData(
	@NotNull BigDecimal preco
) implements IProdutoUpdateData {}
