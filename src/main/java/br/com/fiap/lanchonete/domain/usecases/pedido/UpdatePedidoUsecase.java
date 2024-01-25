package br.com.fiap.lanchonete.domain.usecases.pedido;

import br.com.fiap.lanchonete.domain.entities.Pedido;

public interface UpdatePedidoUsecase {
    Pedido update(Long id, Pedido pedido);
}
