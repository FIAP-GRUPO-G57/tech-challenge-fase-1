package br.com.fiap.lanchonete.application.ports.input;

import br.com.fiap.lanchonete.adapters.driver.dto.ClienteDTO;
import br.com.fiap.lanchonete.adapters.driver.mappers.ClienteMapper;
import br.com.fiap.lanchonete.application.ports.output.ClienteOutputPort;
import br.com.fiap.lanchonete.application.usecase.CreateClienteUseCase;
import br.com.fiap.lanchonete.application.usecase.GetClienteUseCase;
import br.com.fiap.lanchonete.domain.entities.Cliente;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;


@Transactional
@Component
public class ClientInputPort  implements CreateClienteUseCase , GetClienteUseCase {



    private final  ClienteOutputPort clienteOutputPort;


    private final  ClienteMapper clienteMapper;

    public ClientInputPort(ClienteOutputPort clienteOutputPort, ClienteMapper clienteMapper) {
        this.clienteOutputPort = clienteOutputPort;
        this.clienteMapper = clienteMapper;
    }


    @Override
    public ClienteDTO createCliente(ClienteDTO cliente) {
        Cliente cliente1 =   clienteOutputPort.save(clienteMapper.toDomain(cliente));

        return clienteMapper.toDTO(cliente1);
    }

    @Override
    public ClienteDTO retrieveCliente(String cpf) {
        Cliente cliente = clienteOutputPort.findByCpf(cpf);

        return clienteMapper.toDTO(cliente);
    }
}
