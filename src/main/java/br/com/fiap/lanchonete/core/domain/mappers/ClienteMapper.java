package br.com.fiap.lanchonete.core.domain.mappers;

import br.com.fiap.lanchonete.core.domain.dto.ClienteDto;
import br.com.fiap.lanchonete.core.domain.entities.Cliente;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

	private final ModelMapper modelMapper;

	public ClienteMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public Cliente toDomain(ClienteDto dto) {
		return modelMapper.map(dto, Cliente.class);
	}

	public ClienteDto toDTO(Cliente cliente) {
		return modelMapper.map(cliente, ClienteDto.class);
	}

}
