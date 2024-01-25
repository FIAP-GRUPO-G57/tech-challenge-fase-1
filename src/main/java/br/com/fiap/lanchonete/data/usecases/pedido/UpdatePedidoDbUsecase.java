package br.com.fiap.lanchonete.data.usecases.pedido;

import br.com.fiap.lanchonete.data.protocols.db.pedido.GetPedidoRepository;
import br.com.fiap.lanchonete.data.protocols.db.pedido.SavePedidoRepository;
import br.com.fiap.lanchonete.domain.entities.Pedido;
import br.com.fiap.lanchonete.domain.usecases.pedido.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdatePedidoDbUsecase implements UpdatePedidoUsecase {

	private final GetPedidoRepository getPedidoRepository;

	private final SavePedidoRepository savePedidoRepository;

	@Override
	public Pedido update(Long id, Pedido pedido) {
		if (Objects.nonNull(id)) {
			pedido.setId(id);
			Pedido pedido1 = getPedidoRepository.get(id);
			if (Objects.isNull(pedido1))
				return null;
			pedido1.setStatus(pedido.getStatus());
			return savePedidoRepository.save(pedido1);
		}
		return null;
	}

}
