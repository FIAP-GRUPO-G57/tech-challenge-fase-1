package br.com.fiap.lanchonete.adapters.driven.postgress;

import br.com.fiap.lanchonete.adapters.driven.data.ClienteData;
import br.com.fiap.lanchonete.adapters.driven.mappers.ClienteDataMapper;
import br.com.fiap.lanchonete.adapters.driven.repository.ClienteRepository;
import br.com.fiap.lanchonete.application.ports.output.ClienteOutputPort;
import br.com.fiap.lanchonete.domain.entities.Cliente;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

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

        if (clienteData == null ){
            throw new EntityNotFoundException("Cliente nao encontrado para o cpf :: " + cpf);
        }
        Cliente cli = clienteDataMapper.toDomain(clienteData);

        return cli;
    }
}
