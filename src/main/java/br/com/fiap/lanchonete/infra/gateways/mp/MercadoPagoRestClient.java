package br.com.fiap.lanchonete.infra.gateways.mp;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.fiap.lanchonete.core.domain.entities.OrdemPedido;
import br.com.fiap.lanchonete.core.usecases.ports.gateways.PaymentGatewayPort;
import br.com.fiap.lanchonete.infra.gateways.mp.dto.merchantOrder.MerchantOrderResponse;
import br.com.fiap.lanchonete.infra.gateways.mp.dto.order.Order;
import br.com.fiap.lanchonete.infra.gateways.mp.dto.order.QuickResponse;
import br.com.fiap.lanchonete.infra.gateways.mp.dto.payment.PaymentsResponse;
import br.com.fiap.lanchonete.infra.gateways.mp.mappers.OrdemPedidoMapper;

import java.net.URI;

@Slf4j
@Component
public class MercadoPagoRestClient implements PaymentGatewayPort {

    public static final String POST_ORDER = "https://api.mercadopago.com/instore/orders/qr/seller/collectors/%d/pos/%s/qrs";
    
    public static final String GET_MERCHANT_ORDERS = "https://api.mercadopago.com/merchant_orders/%d";
    
    public static final String GET_PAYMENTS = "https://api.mercadopago.com/v1/payments/%d";
    
    @Autowired
	private OrdemPedidoMapper ordemPedidoMapper;

    public String getPaymentQrCode(Order orderRequest, Long collector, String pos, String token){
        try {
            RestTemplate restTemplate = new RestTemplate();

            String url = String.format(POST_ORDER, collector, pos);
            URI uri = new URI(url);

            HttpEntity<Order> entity = new HttpEntity<>(orderRequest, getHttpHeaders(token));

            ResponseEntity<QuickResponse> result = restTemplate.postForEntity(uri, entity, QuickResponse.class);
            log.info("response order mp: "+result.toString());

            return result.getBody().getQrData();

        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }
    
    public OrdemPedido getMerchantOrder(Long id, String token) {
		try {
			RestTemplate restTemplate = new RestTemplate();

			String url = String.format(GET_MERCHANT_ORDERS, id);

			HttpEntity<Order> entity = new HttpEntity<>(getHttpHeaders(token));

			ResponseEntity<MerchantOrderResponse> result = restTemplate.exchange(url, HttpMethod.GET, entity,
					MerchantOrderResponse.class);

			log.info("response merchant order mp: " + result.toString());

			MerchantOrderResponse merchantOrderResponse = result.getBody();

			return ordemPedidoMapper.toDomain(merchantOrderResponse);

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
    
    public OrdemPedido getPayment(Long id, String token) {
		try {
			RestTemplate restTemplate = new RestTemplate();

			String url = String.format(GET_PAYMENTS, id);

			HttpEntity<Order> entity = new HttpEntity<>(getHttpHeaders(token));

			ResponseEntity<PaymentsResponse> result = restTemplate.exchange(url, HttpMethod.GET, entity,
					PaymentsResponse.class);

			log.info("response payment mp: " + result.toString());

			PaymentsResponse paymentsResponse = result.getBody();

			return ordemPedidoMapper.toDomain(paymentsResponse);

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

    private HttpHeaders getHttpHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer "+token);
        return headers;
    }

}
