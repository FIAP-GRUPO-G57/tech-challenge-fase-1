package br.com.fiap.lanchonete.adapters.driven.mappers;

import br.com.fiap.lanchonete.adapters.driven.data.ClienteData;
import br.com.fiap.lanchonete.domain.entities.Cliente;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class ClienteDataMapper {

    //Criar o método toDomain e toDTO entre as classes ClienteData e Cliente

    private final ModelMapper modelMapper;

    public ClienteDataMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public Cliente toDomain(ClienteData data) {


        return modelMapper.map(data, Cliente.class);
    }

    public ClienteData toData(Cliente cliente) {


        return modelMapper.map(cliente, ClienteData.class);
    }
}
