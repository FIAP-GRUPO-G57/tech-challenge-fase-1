package br.com.fiap.lanchonete.domain.dto;

import br.com.fiap.lanchonete.domain.enums.StatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PedidoDto {
	private Long id;
	@NotNull
	private ClienteDto cliente;
	@NotNull
	private List<ItemDto> itens;
	private BigDecimal preco;
	private StatusEnum status;
}
