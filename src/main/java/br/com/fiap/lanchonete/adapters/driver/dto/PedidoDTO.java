package br.com.fiap.lanchonete.adapters.driver.dto;

import br.com.fiap.lanchonete.adapters.driver.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PedidoDTO {
    private Long id;
    private ClienteDTO cliente;
    @NotNull private List<ItemDTO> itens;
    private BigDecimal preco;
    private Status status;
    private String qrData;
    private Long externalReference;
    private Long paymentId;
    private Status statusPagamento;
    private LocalDateTime criacao;
}