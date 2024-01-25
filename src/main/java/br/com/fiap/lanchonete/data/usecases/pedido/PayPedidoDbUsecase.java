package br.com.fiap.lanchonete.data.usecases.pedido;

import br.com.fiap.lanchonete.data.protocols.db.pedido.PayPedidoRepository;
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
public class PayPedidoDbUsecase implements PayPedidoUsecase {

	private final PayPedidoRepository payPedidoRepository;

	@Override
    public Pedido payPedido(Pedido pedido) {
        if (Objects.isNull(pedido) || Objects.isNull(pedido.getPaymentId()))
            throw new EntityNotFoundException("Payment nao encontrado para o id :: " + pedido.getPaymentId());

        return payPedidoRepository.pay(pedido);
    }

}
