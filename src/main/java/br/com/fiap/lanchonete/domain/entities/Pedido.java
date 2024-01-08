package br.com.fiap.lanchonete.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import br.com.fiap.lanchonete.domain.enums.StatusEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {
	private Long id;
	private Cliente cliente;
	private List<Item> itens;
	private BigDecimal preco;
	private StatusEnum status;
	private LocalDateTime criacao;

	@Override
	public String toString() {
		return "Pedido: " + id + " Cliente: " + cliente.getNome() + " Status: " + status;
	}
}
