package br.com.fiap.lanchonete.core.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import br.com.fiap.lanchonete.core.domain.enums.StatusEnum;

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
