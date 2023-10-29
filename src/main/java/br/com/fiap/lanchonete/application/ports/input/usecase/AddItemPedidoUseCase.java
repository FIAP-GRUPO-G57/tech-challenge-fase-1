package br.com.fiap.lanchonete.application.ports.input.usecase;

import br.com.fiap.lanchonete.domain.entities.Item;
import br.com.fiap.lanchonete.domain.entities.Pedido;

import java.util.List;

public interface AddItemPedidoUseCase {
    Pedido addItemPedido(Long id, List<Item> itens);
}
