package br.com.fiap.lanchonete.data.usecases.pedido;

import br.com.fiap.lanchonete.data.protocols.db.itemPedido.DeleteItemPedidoRepository;
import br.com.fiap.lanchonete.data.protocols.db.pedido.GetPedidoRepository;
import br.com.fiap.lanchonete.data.protocols.db.pedido.SavePedidoRepository;
import br.com.fiap.lanchonete.domain.entities.Item;
import br.com.fiap.lanchonete.domain.entities.Pedido;
import br.com.fiap.lanchonete.domain.enums.StatusEnum;
import br.com.fiap.lanchonete.domain.usecases.itemPedido.DeleteItemPedidoUsecase;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteItemPedidoDbUsecase implements DeleteItemPedidoUsecase {

	private final GetPedidoRepository getPedidoRepository;

	private final DeleteItemPedidoRepository deleteItemPedidoRepository;

	private final SavePedidoRepository savePedidoRepository;

	@Override
	public void deleteItemPedido(Long id, Long idItem) {
		Pedido pedido = getPedidoRepository.get(id);

		if (Objects.isNull(pedido))
			throw new EntityNotFoundException("Pedido nao encontrado para o id :: " + id);

		if (!pedido.getStatus().equals(StatusEnum.CRIADO))
			throw new EntityNotFoundException("Pedido já encaminhado nao pode ser mais alterado :: " + id);

		Item item = pedido.getItens().stream().filter(i -> i.getId().equals(idItem)).findFirst().orElse(null);
		if (Objects.isNull(item))
			throw new EntityNotFoundException("Item nao encontrado para o id :: " + idItem);

		pedido.getItens().remove(item);
		pedido.setPreco(pedido.getPreco().subtract(item.getPreco()));
		deleteItemPedidoRepository.deleteItemPedido(item.getId());
		savePedidoRepository.save(pedido);
	}

}
