package br.com.fiap.lanchonete.data.usecases.pedido;

import br.com.fiap.lanchonete.data.protocols.db.pedido.GetPedidoRepository;
import br.com.fiap.lanchonete.domain.entities.Pedido;
import br.com.fiap.lanchonete.domain.usecases.pedido.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class GetPedidoByIdDbUsecase implements GetPedidoByIdUsecase {

	private final GetPedidoRepository getPedidoRepository;

	@Override
	public Pedido get(Long id) {
		return getPedidoRepository.get(id);
	}

}
