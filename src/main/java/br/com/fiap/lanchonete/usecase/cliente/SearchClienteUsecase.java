package br.com.fiap.lanchonete.usecase.cliente;

import br.com.fiap.lanchonete.entity.cliente.gateway.ClienteGateway;
import br.com.fiap.lanchonete.entity.cliente.model.Cliente;

import java.util.List;

public class SearchClienteUsecase {
    private final ClienteGateway clienteGateway;

    public SearchClienteUsecase(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public List<Cliente> execute() {
        return clienteGateway.findAll();
    }
}
