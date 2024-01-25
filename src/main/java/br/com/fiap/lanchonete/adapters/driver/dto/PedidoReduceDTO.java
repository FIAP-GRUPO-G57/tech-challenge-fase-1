package br.com.fiap.lanchonete.adapters.driver.dto;

import br.com.fiap.lanchonete.adapters.driver.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PedidoReduceDTO {
    private Long id;
    private Status status;
    private LocalDateTime criacao;
    private String tempo;
}
