package br.com.fiap.lanchonete.core.usecases.ports.repositories;

import br.com.fiap.lanchonete.core.domain.entities.Item;

public interface ItemPedidoRepositoryPort {
	Item addItemPedido(Long id, Item itens);

	void deleteItemPedido(Long idItem);
}
