package br.com.fiap.lanchonete.data.usecases.pedido;

import br.com.fiap.lanchonete.data.protocols.db.pedido.ConfirmPedidoRepository;
import br.com.fiap.lanchonete.domain.entities.Pedido;
import br.com.fiap.lanchonete.domain.usecases.pedido.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ConfirmPedidoDbUsecase implements ConfirmPedidoUsecase {

	private final ConfirmPedidoRepository confirmPedidoRepository;

	@Override
    public Pedido confirmPedido(Pedido pedido) {
        if (Objects.isNull(pedido) || Objects.isNull(pedido.getOrderId()))
            throw new EntityNotFoundException("Order nao encontrado para o id :: " + pedido.getOrderId());
        return confirmPedidoRepository.confirm(pedido);
    }

}
