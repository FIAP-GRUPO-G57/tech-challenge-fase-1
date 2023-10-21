package br.com.fiap.lanchonete.adapters.configuration;

import br.com.fiap.lanchonete.adapters.driver.mappers.ClienteMapper;
import br.com.fiap.lanchonete.application.ports.output.ClienteOutputPort;
import br.com.fiap.lanchonete.domain.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClienteServiceConfiguration {


    @Autowired
    private  ClienteOutputPort clienteOutputPort;


    @Autowired
    private  ClienteMapper clienteMapper;

    @Bean
    public ClienteService clienteService() {
        return new ClienteService(clienteOutputPort, clienteMapper);
    }
}
