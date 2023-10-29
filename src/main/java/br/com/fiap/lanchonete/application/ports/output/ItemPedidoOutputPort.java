package br.com.fiap.lanchonete.application.ports.output;

import br.com.fiap.lanchonete.domain.entities.Item;

import java.util.List;

public interface ItemPedidoOutputPort {

    void deleteItemPedido( Long idItem);

    Item addItemPedido(Long id, Item itens);
}
