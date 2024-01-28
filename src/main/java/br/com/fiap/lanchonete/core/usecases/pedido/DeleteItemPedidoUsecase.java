package br.com.fiap.lanchonete.core.usecases.pedido;

import br.com.fiap.lanchonete.core.domain.entities.Item;
import br.com.fiap.lanchonete.core.domain.entities.Pedido;
import br.com.fiap.lanchonete.core.domain.enums.StatusEnum;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.ItemPedidoRepositoryPort;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.PedidoRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteItemPedidoUsecase {

	private final PedidoRepositoryPort pedidoPort;

	private final ItemPedidoRepositoryPort itemPedidoPort;

	public void deleteItemPedido(Long id, Long idItem) {
		Pedido pedido = pedidoPort.get(id);

		if (Objects.isNull(pedido))
			throw new EntityNotFoundException("Pedido nao encontrado para o id :: " + id);

		if (!pedido.getStatus().equals(StatusEnum.CRIADO))
			throw new EntityNotFoundException("Pedido já encaminhado nao pode ser mais alterado :: " + id);

		Item item = pedido.getItens().stream().filter(i -> i.getId().equals(idItem)).findFirst().orElse(null);
		if (Objects.isNull(item))
			throw new EntityNotFoundException("Item nao encontrado para o id :: " + idItem);

		pedido.getItens().remove(item);
		pedido.setPreco(pedido.getPreco().subtract(item.getPreco()));
		itemPedidoPort.deleteItemPedido(item.getId());
		pedidoPort.save(pedido);
	}

}
