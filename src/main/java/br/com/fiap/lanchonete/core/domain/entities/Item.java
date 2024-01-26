package br.com.fiap.lanchonete.core.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {
	private Long id;
	private Pedido pedido;
	private Produto produto;
	private BigDecimal preco;
}
