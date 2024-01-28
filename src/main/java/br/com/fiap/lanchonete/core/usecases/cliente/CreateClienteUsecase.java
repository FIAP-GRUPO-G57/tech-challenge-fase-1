package br.com.fiap.lanchonete.core.usecases.cliente;

import java.util.Optional;

import br.com.fiap.lanchonete.core.domain.dto.ClienteDto;
import br.com.fiap.lanchonete.core.domain.entities.Cliente;
import br.com.fiap.lanchonete.core.domain.mappers.ClienteMapper;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.ClienteRepositoryPort;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;

@Transactional
public class CreateClienteUsecase {

	private final ClienteRepositoryPort clientePort;

	private final ClienteMapper clienteMapper;

	public CreateClienteUsecase(ClienteRepositoryPort clientePort, ClienteMapper clienteMapper) {
		this.clientePort = clientePort;
		this.clienteMapper = clienteMapper;
	}

	public ClienteDto createCliente(ClienteDto cliente) {

		Cliente cliente2 = Optional.ofNullable(cliente).map(ClienteDto::getCpf)
				.map(cpf -> clientePort.findByCpf(cpf)).orElse(null);

		if (cliente2 != null) {
			throw new EntityExistsException("Cliente ja cadastrado para o cpf :: " + cliente.getCpf());
		}

		Cliente cliente1 = clientePort.save(clienteMapper.toDomain(cliente));

		return clienteMapper.toDTO(cliente1);
	}

}
