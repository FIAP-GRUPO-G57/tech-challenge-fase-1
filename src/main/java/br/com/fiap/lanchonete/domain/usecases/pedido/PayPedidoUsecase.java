package br.com.fiap.lanchonete.domain.usecases.pedido;

import br.com.fiap.lanchonete.domain.entities.Pedido;

public interface PayPedidoUsecase {
    Pedido payPedido(Pedido pedido);
}
