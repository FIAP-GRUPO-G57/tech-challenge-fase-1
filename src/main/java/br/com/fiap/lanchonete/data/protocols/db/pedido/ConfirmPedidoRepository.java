package br.com.fiap.lanchonete.data.protocols.db.pedido;

import br.com.fiap.lanchonete.domain.entities.Pedido;

public interface ConfirmPedidoRepository {
	Pedido confirm(Pedido pedido);
}
