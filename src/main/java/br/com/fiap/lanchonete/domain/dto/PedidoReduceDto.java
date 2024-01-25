package br.com.fiap.lanchonete.domain.dto;

import br.com.fiap.lanchonete.domain.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PedidoReduceDto {
    private Long id;
    private StatusEnum status;
    private LocalDateTime criacao;
    private String tempo;
}
