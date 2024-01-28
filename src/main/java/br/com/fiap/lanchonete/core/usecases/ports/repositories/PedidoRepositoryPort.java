package br.com.fiap.lanchonete.core.usecases.ports.repositories;

import java.util.List;

import br.com.fiap.lanchonete.core.domain.entities.OrdemPedido;
import br.com.fiap.lanchonete.core.domain.entities.Pedido;

public interface PedidoRepositoryPort {
	Pedido checkout(Pedido pedido);

	Pedido confirm(Pedido pedido, OrdemPedido ordemPedido);

	List<Pedido> findAll();

	List<Pedido> findByStatus(List<String> statuss);

	Pedido get(Long id);

	Pedido pay(Pedido pedido, OrdemPedido ordemPedido);

	Pedido save(Pedido pedido);
}
