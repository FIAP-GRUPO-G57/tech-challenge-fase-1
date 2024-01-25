package br.com.fiap.lanchonete.data.protocols.db.pedido;

import java.util.List;

import br.com.fiap.lanchonete.domain.entities.Pedido;

public interface FindPedidoByStatusRepository {
	List<Pedido> findByStatus(List<String> statuss);
}