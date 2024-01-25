package br.com.fiap.lanchonete.domain.usecases.pedido;

import br.com.fiap.lanchonete.domain.entities.Pedido;

import java.util.List;

public interface FindPedidoByStatusUsecase {
	List<Pedido> findByStatus(List<String> statuss);
}
