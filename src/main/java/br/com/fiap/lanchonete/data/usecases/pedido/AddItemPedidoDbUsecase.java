package br.com.fiap.lanchonete.data.usecases.pedido;

import br.com.fiap.lanchonete.data.protocols.db.itemPedido.AddItemPedidoRepository;
import br.com.fiap.lanchonete.data.protocols.db.pedido.GetPedidoRepository;
import br.com.fiap.lanchonete.data.protocols.db.pedido.SavePedidoRepository;
import br.com.fiap.lanchonete.data.protocols.db.produto.GetProdutoRepository;
import br.com.fiap.lanchonete.domain.entities.Item;
import br.com.fiap.lanchonete.domain.entities.Pedido;
import br.com.fiap.lanchonete.domain.entities.Produto;
import br.com.fiap.lanchonete.domain.enums.StatusEnum;
import br.com.fiap.lanchonete.domain.usecases.itemPedido.AddItemPedidoUsecase;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class AddItemPedidoDbUsecase implements AddItemPedidoUsecase {

	private final GetProdutoRepository getProdutoRepository;

	private final GetPedidoRepository getPedidoRepository;
	
	private final SavePedidoRepository savePedidoRepository;
	
	private final AddItemPedidoRepository addItemPedidoRepository;

	@Override
	public Pedido addItemPedido(Long id, List<Item> itens) {
		Pedido pedido = getPedidoRepository.get(id);
		if (Objects.isNull(pedido))
			throw new EntityNotFoundException("Pedido nao encontrado para o id :: " + id);

		if (!pedido.getStatus().equals(StatusEnum.CRIADO))
			throw new EntityNotFoundException("Pedido já encaminhado nao pode ser mais alterado :: " + id);

		Produto produto = null;
		for (Item item : itens) {
			produto = getProdutoRepository.get(item.getProduto().getId());

			if (Objects.isNull(produto))
				throw new EntityNotFoundException("Produto nao encontrado para o id :: " + item.getProduto().getId());

			item.setPreco(produto.getPreco());

			item.setPedido(pedido);
			item.setProduto(produto);

			item = addItemPedidoRepository.addItemPedido(id, item);
			pedido.getItens().add(item);
			pedido.setPreco(pedido.getPreco().add(produto.getPreco()));
		}

		return savePedidoRepository.save(pedido);

	}

}
