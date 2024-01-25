package br.com.fiap.lanchonete.adapters.driven.rest.merchantorder;

import br.com.fiap.lanchonete.adapters.driven.rest.merchantorder.dto.MerchantOrderResponse;
import br.com.fiap.lanchonete.adapters.driven.rest.order.dto.Order;
import br.com.fiap.lanchonete.adapters.driven.rest.order.dto.QuickResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Component
public class MerchantOrderRestClient {

    public static final String GET_MERCHANT_ORDERS = "https://api.mercadopago.com/merchant_orders/%d";

    public MerchantOrderResponse get(Long id, String token){
        try {
            RestTemplate restTemplate = new RestTemplate();

            String url = String.format(GET_MERCHANT_ORDERS, id);

            HttpEntity<Order> entity = new HttpEntity<>(getHttpHeaders(token));

            ResponseEntity<MerchantOrderResponse> result = restTemplate.exchange(url, HttpMethod.GET, entity, MerchantOrderResponse.class);

            log.info("response merchant order mp: "+result.toString());

            return result.getBody();

        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }

    private HttpHeaders getHttpHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", token);
        return headers;
    }

}
