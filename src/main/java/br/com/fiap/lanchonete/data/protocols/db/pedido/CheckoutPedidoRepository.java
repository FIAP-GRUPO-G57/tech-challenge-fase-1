package br.com.fiap.lanchonete.data.protocols.db.pedido;

import br.com.fiap.lanchonete.domain.entities.Pedido;

public interface CheckoutPedidoRepository {
	Pedido checkout(Pedido pedido);
}
