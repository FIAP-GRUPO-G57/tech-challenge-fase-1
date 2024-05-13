package br.com.fiap.lanchonete.usecase.cliente;

import br.com.fiap.lanchonete.entity.cliente.exception.ClienteNotFoundException;
import br.com.fiap.lanchonete.entity.cliente.gateway.ClienteGateway;
import br.com.fiap.lanchonete.entity.cliente.model.Cliente;
import org.springframework.stereotype.Service;

@Service
public class GetClienteUsecase {

    private final ClienteGateway clienteGateway;

    public GetClienteUsecase(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public Cliente execute(Long id) throws ClienteNotFoundException {
        return clienteGateway.findById(id).orElseThrow(ClienteNotFoundException::new);
    }
}
