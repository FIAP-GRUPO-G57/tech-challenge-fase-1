package br.com.fiap.lanchonete.domain.usecases.pedido;

import br.com.fiap.lanchonete.domain.entities.Pedido;

public interface GetPedidoByIdUsecase {
    Pedido get(Long id);
}
