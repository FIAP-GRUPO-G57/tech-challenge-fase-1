package br.com.fiap.lanchonete.application.ports.input.usecase;

import br.com.fiap.lanchonete.domain.entities.Pedido;

public interface UpdatePedidoUseCase {
    Pedido update(Long id, Pedido pedido);
}
