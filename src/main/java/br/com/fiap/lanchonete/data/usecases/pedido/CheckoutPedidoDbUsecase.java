package br.com.fiap.lanchonete.data.usecases.pedido;

import br.com.fiap.lanchonete.data.protocols.db.pedido.GetPedidoRepository;
import br.com.fiap.lanchonete.data.protocols.db.pedido.SavePedidoRepository;
import br.com.fiap.lanchonete.domain.entities.Pedido;
import br.com.fiap.lanchonete.domain.enums.StatusEnum;
import br.com.fiap.lanchonete.domain.usecases.pedido.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CheckoutPedidoDbUsecase implements CheckoutPedidoUsecase {

	private final GetPedidoRepository getPedidoRepository;
	private final SavePedidoRepository savePedidoRepository;

	@Override
	public void checkoutPedido(Long idPedido) {
		Pedido pedido = getPedidoRepository.get(idPedido);
		if (Objects.isNull(pedido))
			throw new EntityNotFoundException("Pedido nao encontrado para o id :: " + idPedido);

		if (!pedido.getStatus().equals(StatusEnum.CRIADO))
			throw new EntityNotFoundException("Pedido já encaminhado nao pode ser mais alterado :: " + idPedido);

		pedido.setStatus(StatusEnum.RECEBIDO);
		savePedidoRepository.save(pedido);
	}

}
