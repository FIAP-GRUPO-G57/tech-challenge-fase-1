package br.com.fiap.lanchonete.infra.db.repositories.postgresql;

import br.com.fiap.lanchonete.data.mappers.ItemDataMapper;
import br.com.fiap.lanchonete.data.protocols.db.itemPedido.AddItemPedidoRepository;
import br.com.fiap.lanchonete.data.protocols.db.itemPedido.DeleteItemPedidoRepository;
import br.com.fiap.lanchonete.domain.entities.Item;
import br.com.fiap.lanchonete.infra.db.repositories.ItemPedidoRepository;
import br.com.fiap.lanchonete.infra.db.schemas.ItemSchema;

import org.springframework.stereotype.Component;

@Component
public class ItemPedidoPostgresqlRepository implements DeleteItemPedidoRepository, AddItemPedidoRepository {

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
