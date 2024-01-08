package br.com.fiap.lanchonete.domain.usecases.cliente;

import br.com.fiap.lanchonete.domain.dto.ClienteDto;

public interface CreateClienteUsecase {
    ClienteDto createCliente(ClienteDto cliente);
}
