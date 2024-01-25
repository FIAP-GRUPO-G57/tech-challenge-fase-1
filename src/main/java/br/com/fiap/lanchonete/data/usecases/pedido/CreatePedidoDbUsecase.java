package br.com.fiap.lanchonete.data.usecases.pedido;

import br.com.fiap.lanchonete.data.protocols.db.cliente.FindClienteByCpfRepository;
import br.com.fiap.lanchonete.data.protocols.db.pedido.SavePedidoRepository;
import br.com.fiap.lanchonete.data.protocols.db.produto.GetProdutoRepository;
import br.com.fiap.lanchonete.domain.entities.Cliente;
import br.com.fiap.lanchonete.domain.entities.Item;
import br.com.fiap.lanchonete.domain.entities.Pedido;
import br.com.fiap.lanchonete.domain.entities.Produto;
import br.com.fiap.lanchonete.domain.enums.StatusEnum;
import br.com.fiap.lanchonete.domain.usecases.pedido.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreatePedidoDbUsecase implements CreatePedidoUsecase {

	private final FindClienteByCpfRepository findClienteByCpfRepository;

	private final GetProdutoRepository getProdutoRepository;

	private final SavePedidoRepository savePedidoRepository;

	@Override
	public Pedido create(Pedido pedido) {
		pedido.setCriacao(LocalDateTime.now());
		pedido.setStatus(StatusEnum.CRIADO);
		pedido.setStatusPagamento(StatusEnum.PENDING);
		pedido.setPreco(BigDecimal.ZERO);
		Cliente cliente = Optional.ofNullable(pedido).map(Pedido::getCliente).map(Cliente::getCpf)
				.map(cpf -> findClienteByCpfRepository.findByCpf(cpf)).orElse(null);
		if (cliente != null) {
			pedido.getCliente().setId(cliente.getId());
			pedido.setCliente(cliente);
		}
		Produto produto = null;
		for (Item item : pedido.getItens()) {
			produto = getProdutoRepository.get(item.getProduto().getId());
			if (Objects.isNull(produto))
				throw new EntityNotFoundException("Produto nao encontrado para o id :: " + item.getProduto().getId());
			item.setPreco(produto.getPreco());
			item.setPedido(pedido);
			item.setProduto(produto);
			pedido.setPreco(pedido.getPreco().add(item.getPreco()));
		}
		return savePedidoRepository.save(pedido);
	}

}
