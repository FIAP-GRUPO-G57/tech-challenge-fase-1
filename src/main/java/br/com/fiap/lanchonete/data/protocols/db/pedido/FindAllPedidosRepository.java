package br.com.fiap.lanchonete.data.protocols.db.pedido;

import java.util.List;

import br.com.fiap.lanchonete.domain.entities.Pedido;

public interface FindAllPedidosRepository {
	List<Pedido> findAll();
}