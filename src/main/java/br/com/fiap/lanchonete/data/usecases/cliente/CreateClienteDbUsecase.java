package br.com.fiap.lanchonete.data.usecases.cliente;


import org.apache.commons.lang3.ObjectUtils;

import br.com.fiap.lanchonete.data.protocols.db.cliente.FindClienteByCpfRepository;
import br.com.fiap.lanchonete.data.protocols.db.cliente.SaveClienteRepository;
import br.com.fiap.lanchonete.domain.dto.ClienteDto;
import br.com.fiap.lanchonete.domain.entities.Cliente;
import br.com.fiap.lanchonete.domain.mappers.ClienteMapper;
import br.com.fiap.lanchonete.domain.usecases.cliente.CreateClienteUsecase;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;


@Transactional
public class CreateClienteDbUsecase implements CreateClienteUsecase {

    private final SaveClienteRepository saveClienteRepository;
    
    private final FindClienteByCpfRepository findClienteByCpfRepository;

    private final ClienteMapper clienteMapper;

    public CreateClienteDbUsecase(SaveClienteRepository saveClienteRepository, FindClienteByCpfRepository findClienteByCpfRepository, ClienteMapper clienteMapper) {
        this.saveClienteRepository = saveClienteRepository;
        this.findClienteByCpfRepository = findClienteByCpfRepository;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public ClienteDto createCliente(ClienteDto clienteDto) {
        boolean clienteAlreadyExists = ObjectUtils.isNotEmpty(findClienteByCpfRepository.findByCpf(clienteDto.getCpf()));

        if (clienteAlreadyExists) {
            throw new EntityExistsException("Cliente ja cadastrado para o cpf :: " + clienteDto.getCpf());
        }

        Cliente cliente = saveClienteRepository.save(clienteMapper.toDomain(clienteDto));
        return clienteMapper.toDTO(cliente);
    }
    
}
