package br.com.fiap.lanchonete.adapters.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "gateway.data.payment")
@Configuration("paymentProperties")
public class GatewayPayment {
    private String notification;
    private String sku;
}
