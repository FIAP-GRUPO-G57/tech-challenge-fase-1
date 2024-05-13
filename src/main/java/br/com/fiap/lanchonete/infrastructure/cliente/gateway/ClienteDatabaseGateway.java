package br.com.fiap.lanchonete.infrastructure.cliente.gateway;

import br.com.fiap.lanchonete.entity.cliente.gateway.ClienteGateway;
import br.com.fiap.lanchonete.entity.cliente.model.Cliente;
import br.com.fiap.lanchonete.infrastructure.config.db.repository.ClienteRepository;
import br.com.fiap.lanchonete.infrastructure.config.db.schema.ClienteSchema;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ClienteDatabaseGateway implements ClienteGateway {

    private final ClienteRepository clienteRepository;

    public ClienteDatabaseGateway(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Optional<Cliente> findByCpf(String cpf) {
        return clienteRepository.findByCpf(cpf).map(ClienteSchema::toCliente);
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll().stream().map(ClienteSchema::toCliente).toList();
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(new ClienteSchema(cliente)).toCliente();
    }
}