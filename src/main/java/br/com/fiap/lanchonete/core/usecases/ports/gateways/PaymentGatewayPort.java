package br.com.fiap.lanchonete.core.usecases.ports.gateways;

import br.com.fiap.lanchonete.core.domain.entities.OrdemPedido;
import br.com.fiap.lanchonete.infra.gateways.mp.dto.order.Order;

public interface PaymentGatewayPort {
	String getPaymentQrCode(Order orderRequest, Long collector, String pos, String token);
	OrdemPedido getPayment(Long id, String token);
	OrdemPedido getMerchantOrder(Long id, String token);	
}
