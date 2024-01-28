package br.com.fiap.lanchonete.infra.db.mappers;

import br.com.fiap.lanchonete.core.domain.entities.Cliente;
import br.com.fiap.lanchonete.infra.db.entities.ClienteEntity;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ClienteDataMapper {

	private final ModelMapper modelMapper;

	public ClienteDataMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public Cliente toDomain(ClienteEntity data) {

		return modelMapper.map(data, Cliente.class);
	}

	public ClienteEntity toData(Cliente cliente) {

		return modelMapper.map(cliente, ClienteEntity.class);
	}
	
}


