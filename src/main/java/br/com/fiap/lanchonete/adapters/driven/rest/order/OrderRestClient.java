package br.com.fiap.lanchonete.adapters.driven.rest.order;

import br.com.fiap.lanchonete.adapters.driven.rest.order.dto.Order;
import br.com.fiap.lanchonete.adapters.driven.rest.order.dto.QuickResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Component
public class OrderRestClient {

    public static final String POST_ORDER = "https://api.mercadopago.com/instore/orders/qr/seller/collectors/%d/pos/%s/qrs";

    public QuickResponse post(Order orderRequest, Long collector, String pos, String token){
        try {
            RestTemplate restTemplate = new RestTemplate();

            String url = String.format(POST_ORDER, collector, pos);
            URI uri = new URI(url);

            HttpEntity<Order> entity = new HttpEntity<>(orderRequest, getHttpHeaders(token));

            ResponseEntity<QuickResponse> result = restTemplate.postForEntity(uri, entity, QuickResponse.class);
            log.info("response order mp: "+result.toString());

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
