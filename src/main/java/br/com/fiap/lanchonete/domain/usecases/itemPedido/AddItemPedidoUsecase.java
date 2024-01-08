package br.com.fiap.lanchonete.domain.usecases.itemPedido;

import br.com.fiap.lanchonete.domain.entities.Item;
import br.com.fiap.lanchonete.domain.entities.Pedido;

import java.util.List;

public interface AddItemPedidoUsecase {
    Pedido addItemPedido(Long id, List<Item> itens);
}
