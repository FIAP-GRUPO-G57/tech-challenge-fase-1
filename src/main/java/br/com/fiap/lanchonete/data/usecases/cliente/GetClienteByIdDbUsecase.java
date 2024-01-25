package br.com.fiap.lanchonete.data.usecases.cliente;

import org.springframework.stereotype.Service;

import br.com.fiap.lanchonete.data.protocols.db.cliente.GetClienteRepository;
import br.com.fiap.lanchonete.domain.dto.ClienteDto;
import br.com.fiap.lanchonete.domain.mappers.ClienteMapper;
import br.com.fiap.lanchonete.domain.usecases.cliente.GetClienteByIdUsecase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GetClienteByIdDbUsecase implements GetClienteByIdUsecase {

	private final GetClienteRepository getClienteRepository;

	private final ClienteMapper clienteMapper;

	@Override
	public ClienteDto getById(Long id) {
		return clienteMapper.toDTO(getClienteRepository.get(id));
	}
}