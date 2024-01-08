package br.com.fiap.lanchonete.data.mappers;

import br.com.fiap.lanchonete.domain.entities.Cliente;
import br.com.fiap.lanchonete.infra.db.schemas.ClienteSchema;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ClienteDataMapper {

	private final ModelMapper modelMapper;

	public ClienteDataMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public Cliente toDomain(ClienteSchema data) {

		return modelMapper.map(data, Cliente.class);
	}

	public ClienteSchema toData(Cliente cliente) {

		return modelMapper.map(cliente, ClienteSchema.class);
	}
	
}
