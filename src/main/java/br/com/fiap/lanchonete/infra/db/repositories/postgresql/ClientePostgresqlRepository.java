package br.com.fiap.lanchonete.infra.db.repositories.postgresql;

import br.com.fiap.lanchonete.data.mappers.ClienteDataMapper;
import br.com.fiap.lanchonete.data.protocols.db.cliente.FindClienteByCpfRepository;
import br.com.fiap.lanchonete.data.protocols.db.cliente.SaveClienteRepository;
import br.com.fiap.lanchonete.domain.entities.Cliente;
import br.com.fiap.lanchonete.infra.db.repositories.ClienteRepository;
import br.com.fiap.lanchonete.infra.db.schemas.ClienteSchema;

import org.springframework.stereotype.Component;

@Component
public class ClientePostgresqlRepository implements SaveClienteRepository, FindClienteByCpfRepository {

	private final ClienteRepository clienteRepository;

	private final ClienteDataMapper clienteDataMapper;

	public ClientePostgresqlRepository(ClienteRepository clienteRepository, ClienteDataMapper clienteDataMapper) {
		this.clienteRepository = clienteRepository;
		this.clienteDataMapper = clienteDataMapper;
	}

	@Override
	public Cliente save(Cliente cliente) {
		ClienteSchema clienteData = clienteDataMapper.toData(cliente);
		clienteData = clienteRepository.save(clienteData);
		Cliente cli = clienteDataMapper.toDomain(clienteData);
		return cli;
	}

	@Override
	public Cliente findByCpf(String cpf) {
		ClienteSchema clienteData = clienteRepository.findByCpf(cpf);
		Cliente cli = null;

		if (clienteData != null) {
			cli = clienteDataMapper.toDomain(clienteData);
		}

		return cli;
	}

}
