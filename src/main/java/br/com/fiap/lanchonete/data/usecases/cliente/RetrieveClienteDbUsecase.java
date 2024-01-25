package br.com.fiap.lanchonete.data.usecases.cliente;

import br.com.fiap.lanchonete.data.protocols.db.cliente.FindClienteByCpfRepository;
import br.com.fiap.lanchonete.domain.dto.ClienteDto;
import br.com.fiap.lanchonete.domain.entities.Cliente;
import br.com.fiap.lanchonete.domain.mappers.ClienteMapper;
import br.com.fiap.lanchonete.domain.usecases.cliente.GetClienteByCpfUsecase;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Transactional
public class RetrieveClienteDbUsecase implements GetClienteByCpfUsecase {

	private final FindClienteByCpfRepository findClienteByCpfRepository;

	private final ClienteMapper clienteMapper;

	public RetrieveClienteDbUsecase(FindClienteByCpfRepository findClienteByCpfRepository,
			ClienteMapper clienteMapper) {
		this.findClienteByCpfRepository = findClienteByCpfRepository;
		this.clienteMapper = clienteMapper;
	}

	@Override
	public ClienteDto retrieveCliente(String cpf) {
		Cliente cliente = findClienteByCpfRepository.findByCpf(cpf);

		if (cliente == null) {
			throw new EntityNotFoundException("Cliente nao encontrado para o cpf :: " + cpf);
		}
		
		return clienteMapper.toDTO(cliente);
	}

}
