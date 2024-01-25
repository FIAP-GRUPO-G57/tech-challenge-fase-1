package br.com.fiap.lanchonete.adapters.driven.postgress;

import br.com.fiap.lanchonete.adapters.driven.data.ClienteData;
import br.com.fiap.lanchonete.adapters.driven.mappers.ClienteDataMapper;
import br.com.fiap.lanchonete.adapters.driven.repository.ClienteRepository;
import br.com.fiap.lanchonete.application.ports.output.ClienteOutputPort;
import br.com.fiap.lanchonete.domain.entities.Cliente;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientePersistencePostgreeAdapter implements ClienteOutputPort {

    private final  ClienteRepository clienteRepository;

    private final ClienteDataMapper clienteDataMapper;

    public ClientePersistencePostgreeAdapter(ClienteRepository clienteRepository, ClienteDataMapper clienteDataMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteDataMapper = clienteDataMapper;
    }

    @Override
    public Cliente save(Cliente cliente) {

        ClienteData clienteData = clienteDataMapper.toData(cliente);

        clienteData = clienteRepository.save(clienteData);

        Cliente cli = clienteDataMapper.toDomain(clienteData);

        return  cli;
    }

    @Override
    public Cliente findByCpf(String cpf) {
        ClienteData clienteData = clienteRepository.findByCpf(cpf);
        Cliente cli = null;
        if (clienteData != null) {
            cli = clienteDataMapper.toDomain(clienteData);
        }
        return cli;
    }

    @Override
    public Cliente get(Long id) {
        return clienteRepository.findById(id).map(clienteData -> clienteDataMapper.toDomain(clienteData)).orElse(null);
    }

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll().stream()
                .map(clienteData-> clienteDataMapper.toDomain(clienteData))
                .toList();
    }
}
