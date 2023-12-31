package br.com.fiap.lanchonete.domain.services;


import br.com.fiap.lanchonete.adapters.driver.dto.ClienteDTO;
import br.com.fiap.lanchonete.adapters.driver.mappers.ClienteMapper;
import br.com.fiap.lanchonete.application.ports.input.usecase.CreateClienteUseCase;
import br.com.fiap.lanchonete.application.ports.input.usecase.GetClienteUseCase;
import br.com.fiap.lanchonete.application.ports.output.ClienteOutputPort;
import br.com.fiap.lanchonete.domain.entities.Cliente;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;


@Transactional
public class ClienteService implements CreateClienteUseCase, GetClienteUseCase {


    private final ClienteOutputPort clienteOutputPort;


    private final ClienteMapper clienteMapper;

    public ClienteService(ClienteOutputPort clienteOutputPort, ClienteMapper clienteMapper) {
        this.clienteOutputPort = clienteOutputPort;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public ClienteDTO createCliente(ClienteDTO cliente) {

        Cliente cliente2  =  clienteOutputPort.findByCpf(cliente.getCpf());

        if (cliente2 != null) {

            throw new EntityExistsException("Cliente ja cadastrado para o cpf :: " + cliente.getCpf());
        }

        Cliente cliente1 =   clienteOutputPort.save(clienteMapper.toDomain(cliente));

        return clienteMapper.toDTO(cliente1);
    }

    @Override
    public ClienteDTO retrieveCliente(String cpf) {
        Cliente cliente = clienteOutputPort.findByCpf(cpf);

        if (cliente == null ){
            throw new EntityNotFoundException("Cliente nao encontrado para o cpf :: " + cpf);
        }
        return clienteMapper.toDTO(cliente);
    }
}
