package br.com.fiap.lanchonete.data.protocols.db.pedido;

import java.util.List;

import br.com.fiap.lanchonete.domain.entities.Pedido;
import br.com.fiap.lanchonete.domain.enums.StatusEnum;

public interface FindPedidoByStatusRepository {
	List<Pedido> findByStatus(StatusEnum status);
}