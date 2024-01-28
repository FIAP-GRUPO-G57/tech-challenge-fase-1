package br.com.fiap.lanchonete.core.usecases.cliente;

import br.com.fiap.lanchonete.core.domain.dto.ClienteDto;
import br.com.fiap.lanchonete.core.domain.entities.Cliente;
import br.com.fiap.lanchonete.core.domain.mappers.ClienteMapper;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.ClienteRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Transactional
public class RetrieveClienteUsecase {

	private final ClienteRepositoryPort clientePort;

	private final ClienteMapper clienteMapper;

	public RetrieveClienteUsecase(ClienteRepositoryPort clientePort, ClienteMapper clienteMapper) {
		this.clientePort = clientePort;
		this.clienteMapper = clienteMapper;
	}

	public ClienteDto retrieveCliente(String cpf) {
		Cliente cliente = clientePort.findByCpf(cpf);

		if (cliente == null) {
			throw new EntityNotFoundException("Cliente nao encontrado para o cpf :: " + cpf);
		}

		return clienteMapper.toDTO(cliente);
	}

}
