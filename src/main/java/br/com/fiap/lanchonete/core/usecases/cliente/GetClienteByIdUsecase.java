package br.com.fiap.lanchonete.core.usecases.cliente;

import org.springframework.stereotype.Service;
import br.com.fiap.lanchonete.core.domain.entities.Cliente;

import br.com.fiap.lanchonete.core.domain.dto.ClienteDto;
import br.com.fiap.lanchonete.core.domain.mappers.ClienteMapper;
import br.com.fiap.lanchonete.core.usecases.ports.repositories.ClienteRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GetClienteByIdUsecase {

	private final ClienteRepositoryPort clientePort;

	private final ClienteMapper clienteMapper;

	public ClienteDto getById(Long id) {

		Cliente cliente = clientePort.get(id);

		if (cliente == null) {
			throw new EntityNotFoundException("Cliente nao encontrado para o Id :: " + id);
		}
		return clienteMapper.toDTO(cliente);
	}
}