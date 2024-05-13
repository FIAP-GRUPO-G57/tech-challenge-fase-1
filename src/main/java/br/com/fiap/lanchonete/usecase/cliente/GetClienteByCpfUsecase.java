package br.com.fiap.lanchonete.usecase.cliente;

import br.com.fiap.lanchonete.entity.cliente.exception.ClienteNotFoundException;
import br.com.fiap.lanchonete.entity.cliente.gateway.ClienteGateway;
import br.com.fiap.lanchonete.entity.cliente.model.Cliente;

public class GetClienteByCpfUsecase {

    private final ClienteGateway clienteGateway;

    public GetClienteByCpfUsecase(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public Cliente execute(String cpf) throws ClienteNotFoundException {
        return clienteGateway.findByCpf(cpf).orElseThrow(ClienteNotFoundException::new);
    }
}
