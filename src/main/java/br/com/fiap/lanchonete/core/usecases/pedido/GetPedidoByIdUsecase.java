package br.com.fiap.lanchonete.core.usecases.pedido;

import br.com.fiap.lanchonete.core.domain.entities.Pedido;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.PedidoRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class GetPedidoByIdUsecase {

	private final PedidoRepositoryPort pedidoPort;

	public Pedido get(Long id) {
		return pedidoPort.get(id);
	}

}
