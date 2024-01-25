package br.com.fiap.lanchonete.application.ports.input.usecase;

import br.com.fiap.lanchonete.adapters.driver.dto.ClienteDTO;

public interface GetClienteByIdUseCase {
    ClienteDTO getById(Long id);
}
