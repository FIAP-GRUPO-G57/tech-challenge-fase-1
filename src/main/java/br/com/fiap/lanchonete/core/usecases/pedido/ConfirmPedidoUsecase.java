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
public class ConfirmPedidoUsecase {

	private final PedidoRepositoryPort pedidoPort;
	
	private final PaymentGatewayPort paymentGatewayPort;

	private final GatewayPaymentConfiguration gatewayPayment;

    public Pedido confirmPedido(Pedido pedido) {
        if (Objects.isNull(pedido) || Objects.isNull(pedido.getOrderId()))
            throw new EntityNotFoundException("Order nao encontrado para o id :: " + pedido.getOrderId());
        
        OrdemPedido ordemPedido = paymentGatewayPort.getMerchantOrder(pedido.getOrderId(),
				gatewayPayment.getToken());
        
        return pedidoPort.confirm(pedido, ordemPedido);
    }

}
