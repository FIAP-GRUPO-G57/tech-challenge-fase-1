package br.com.fiap.lanchonete.adapters.driven.postgress;

import br.com.fiap.lanchonete.adapters.driven.data.ItemData;
import br.com.fiap.lanchonete.adapters.driven.mappers.ItemDataMapper;
import br.com.fiap.lanchonete.adapters.driven.repository.ItemPedidoRepository;
import br.com.fiap.lanchonete.adapters.driver.mappers.ItemMapper;
import br.com.fiap.lanchonete.application.ports.output.ItemPedidoOutputPort;
import br.com.fiap.lanchonete.domain.entities.Item;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemPedidoPersistencePostgreeAdapter implements ItemPedidoOutputPort {

    private final ItemPedidoRepository itemPedidoRepository;

    private final ItemDataMapper itemDataMapper;

    public ItemPedidoPersistencePostgreeAdapter(ItemPedidoRepository itemPedidoRepository, ItemDataMapper itemDataMapper) {
        this.itemPedidoRepository = itemPedidoRepository;
        this.itemDataMapper = itemDataMapper;
    }

    @Override
    public void deleteItemPedido(Long idItem) {
        itemPedidoRepository.deleteById(idItem);
    }

    @Override
    public Item addItemPedido(Long id, Item itens) {

        ItemData itemData = itemDataMapper.toData(itens);

        itemPedidoRepository.save(itemData);


        return itemDataMapper.toDomain(itemData);
    }
}
