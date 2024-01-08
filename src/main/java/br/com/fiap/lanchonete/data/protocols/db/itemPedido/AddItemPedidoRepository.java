package br.com.fiap.lanchonete.data.protocols.db.itemPedido;

import br.com.fiap.lanchonete.domain.entities.Item;

public interface AddItemPedidoRepository {
	Item addItemPedido(Long id, Item itens);
}
