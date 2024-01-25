package br.com.fiap.lanchonete.adapters.driven.rest.payments;

import br.com.fiap.lanchonete.adapters.driven.rest.order.dto.Order;
import br.com.fiap.lanchonete.adapters.driven.rest.payments.dto.PaymentsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class PaymentsRestClient {

    public static final String GET_PAYMENTS = "https://api.mercadopago.com/v1/payments/%d";

    public PaymentsResponse get(Long id, String token){
        try {
            RestTemplate restTemplate = new RestTemplate();

            String url = String.format(GET_PAYMENTS, id);

            HttpEntity<Order> entity = new HttpEntity<>(getHttpHeaders(token));

            ResponseEntity<PaymentsResponse> result = restTemplate.exchange(url, HttpMethod.GET, entity, PaymentsResponse.class);

            log.info("response payment mp: "+result.toString());

            return result.getBody();

        }catch (Exception e){
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
