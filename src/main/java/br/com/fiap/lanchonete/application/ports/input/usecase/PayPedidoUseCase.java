package br.com.fiap.lanchonete.application.ports.input.usecase;

import br.com.fiap.lanchonete.domain.entities.Pedido;

public interface PayPedidoUseCase {
    Pedido payPedido(Pedido pedido);
}
