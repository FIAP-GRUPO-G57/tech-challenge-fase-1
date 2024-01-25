package br.com.fiap.lanchonete.application.ports.input.usecase;

import br.com.fiap.lanchonete.domain.entities.Pedido;
import br.com.fiap.lanchonete.domain.vo.Status;

import java.util.List;

public interface GetPedidoUseCase {
    List<Pedido> findByStatus(List<String> statuss);
}
