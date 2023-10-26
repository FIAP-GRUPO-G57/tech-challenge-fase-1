package br.com.fiap.lanchonete.domain.entities;

import br.com.fiap.lanchonete.domain.vo.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {
    private Long id;
    private Cliente cliente;
    private List<Item> itens;
    private BigDecimal preco;
    private Status status;
    private LocalDateTime criacao;
}
