package br.com.fiap.lanchonete.infra.db.postgresql;

import br.com.fiap.lanchonete.core.domain.entities.Cliente;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.ClienteRepositoryPort;
import br.com.fiap.lanchonete.infra.db.entities.ClienteEntity;
import br.com.fiap.lanchonete.infra.db.mappers.ClienteDataMapper;
import br.com.fiap.lanchonete.infra.db.repositories.ClienteRepository;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientePostgresqlRepository implements ClienteRepositoryPort {

	private final ClienteRepository clienteRepository;

	private final ClienteDataMapper clienteDataMapper;

	public ClientePostgresqlRepository(ClienteRepository clienteRepository, ClienteDataMapper clienteDataMapper) {
		this.clienteRepository = clienteRepository;
		this.clienteDataMapper = clienteDataMapper;
	}

	@Override
	public Cliente save(Cliente cliente) {
		ClienteEntity clienteSchema = clienteDataMapper.toData(cliente);
		clienteSchema = clienteRepository.save(clienteSchema);
		Cliente cli = clienteDataMapper.toDomain(clienteSchema);
		return cli;
	}

	@Override
	public Cliente findByCpf(String cpf) {
		ClienteEntity clienteSchema = clienteRepository.findByCpf(cpf);
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
		return clienteRepository.findAll().stream().map(clienteData -> clienteDataMapper.toDomain(clienteData))
				.toList();
	}
	
}
