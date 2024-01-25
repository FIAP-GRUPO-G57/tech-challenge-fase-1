package br.com.fiap.lanchonete.infra.db.repositories.postgresql;

import br.com.fiap.lanchonete.data.mappers.ClienteDataMapper;
import br.com.fiap.lanchonete.data.protocols.db.cliente.FindAllClientesRepository;
import br.com.fiap.lanchonete.data.protocols.db.cliente.FindClienteByCpfRepository;
import br.com.fiap.lanchonete.data.protocols.db.cliente.GetClienteRepository;
import br.com.fiap.lanchonete.data.protocols.db.cliente.SaveClienteRepository;
import br.com.fiap.lanchonete.domain.entities.Cliente;
import br.com.fiap.lanchonete.infra.db.repositories.ClienteRepository;
import br.com.fiap.lanchonete.infra.db.schemas.ClienteSchema;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientePostgresqlRepository implements SaveClienteRepository, FindClienteByCpfRepository, GetClienteRepository, FindAllClientesRepository {

    private final  ClienteRepository clienteRepository;

    private final ClienteDataMapper clienteDataMapper;

    public ClientePostgresqlRepository(ClienteRepository clienteRepository, ClienteDataMapper clienteDataMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteDataMapper = clienteDataMapper;
    }

    @Override
    public Cliente save(Cliente cliente) {
        ClienteSchema clienteSchema = clienteDataMapper.toData(cliente);
        clienteSchema = clienteRepository.save(clienteSchema);
        Cliente cli = clienteDataMapper.toDomain(clienteSchema);
        return  cli;
    }

    @Override
    public Cliente findByCpf(String cpf) {
    	ClienteSchema clienteSchema = clienteRepository.findByCpf(cpf);
        Cliente cli = null;
        if (clienteSchema != null) {
            cli = clienteDataMapper.toDomain(clienteSchema);
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
