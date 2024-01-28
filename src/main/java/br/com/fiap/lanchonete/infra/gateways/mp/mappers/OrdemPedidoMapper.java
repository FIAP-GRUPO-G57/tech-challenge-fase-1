package br.com.fiap.lanchonete.infra.gateways.mp.mappers;

import br.com.fiap.lanchonete.core.domain.entities.OrdemPedido;
import br.com.fiap.lanchonete.infra.gateways.mp.dto.merchantOrder.MerchantOrderResponse;
import br.com.fiap.lanchonete.infra.gateways.mp.dto.payment.PaymentsResponse;

import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class OrdemPedidoMapper {

	public OrdemPedido toDomain(MerchantOrderResponse merchantOrderResponse) {
		Long externalReference = Optional.ofNullable(merchantOrderResponse).map(MerchantOrderResponse::getExternalReference)
				.map(Long::parseLong).orElse(null);
		return new OrdemPedido(externalReference);
	}

	public OrdemPedido toDomain(PaymentsResponse paymentsResponse) {
		Long externalReference = Optional.ofNullable(paymentsResponse).map(PaymentsResponse::getExternalReference)
				.map(Long::parseLong).orElse(null);
		return new OrdemPedido(externalReference, paymentsResponse.getStatus());
	}

}
