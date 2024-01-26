package br.com.fiap.lanchonete.core.usecases.pedido;

import br.com.fiap.lanchonete.core.domain.entities.OrdemPedido;
import br.com.fiap.lanchonete.core.domain.entities.Pedido;
import br.com.fiap.lanchonete.core.usecases.ports.gateways.PaymentGatewayPort;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.PedidoRepositoryPort;
import br.com.fiap.lanchonete.main.configuration.GatewayPaymentConfiguration;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PayPedidoUsecase {

	private final PedidoRepositoryPort pedidoRepositoryPort;
	
	private final PaymentGatewayPort paymentGatewayPort;
	
	private final GatewayPaymentConfiguration gatewayPayment;

    public Pedido payPedido(Pedido pedido) {
        if (Objects.isNull(pedido) || Objects.isNull(pedido.getPaymentId()))
            throw new EntityNotFoundException("Payment nao encontrado para o id :: " + pedido.getPaymentId());

        OrdemPedido ordemPedido = paymentGatewayPort.getPayment(pedido.getPaymentId(), gatewayPayment.getToken());
        
        return pedidoRepositoryPort.pay(pedido, ordemPedido);
    }

}
