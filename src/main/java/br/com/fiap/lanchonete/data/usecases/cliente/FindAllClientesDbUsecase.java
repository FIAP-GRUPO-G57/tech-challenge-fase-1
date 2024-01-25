package br.com.fiap.lanchonete.data.usecases.cliente;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fiap.lanchonete.data.protocols.db.cliente.FindAllClientesRepository;
import br.com.fiap.lanchonete.domain.dto.ClienteDto;
import br.com.fiap.lanchonete.domain.mappers.ClienteMapper;
import br.com.fiap.lanchonete.domain.usecases.cliente.GetClienteUsecase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FindAllClientesDbUsecase implements GetClienteUsecase {
	
	private final FindAllClientesRepository findAllClientesRepository;

	private final ClienteMapper clienteMapper;

	@Override
	public List<ClienteDto> getAll() {
		return findAllClientesRepository.findAll().stream().map(clienteData -> clienteMapper.toDTO(clienteData))
				.toList();
	}
	
}
