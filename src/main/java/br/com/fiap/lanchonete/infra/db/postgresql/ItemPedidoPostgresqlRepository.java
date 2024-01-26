package br.com.fiap.lanchonete.infra.db.postgresql;

import br.com.fiap.lanchonete.core.domain.entities.Item;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.ItemPedidoRepositoryPort;
import br.com.fiap.lanchonete.infra.db.mappers.ItemDataMapper;
import br.com.fiap.lanchonete.infra.db.repositories.ItemPedidoRepository;
import br.com.fiap.lanchonete.infra.db.schemas.ItemSchema;

import org.springframework.stereotype.Component;

@Component
public class ItemPedidoPostgresqlRepository implements ItemPedidoRepositoryPort {

	private final ItemPedidoRepository itemPedidoRepository;

	private final ItemDataMapper itemDataMapper;

	public ItemPedidoPostgresqlRepository(ItemPedidoRepository itemPedidoRepository, ItemDataMapper itemDataMapper) {
		this.itemPedidoRepository = itemPedidoRepository;
		this.itemDataMapper = itemDataMapper;
	}

	@Override
	public void deleteItemPedido(Long idItem) {
		itemPedidoRepository.deleteById(idItem);
	}

	@Override
	public Item addItemPedido(Long id, Item itens) {
		ItemSchema itemData = itemDataMapper.toData(itens);
		itemPedidoRepository.save(itemData);
		return itemDataMapper.toDomain(itemData);
	}
}
