package br.com.fiap.lanchonete.application.ports.input.usecase;

import br.com.fiap.lanchonete.adapters.driver.dto.ClienteDTO;

import java.util.List;


public interface GetClienteUseCase {

    List<ClienteDTO> getAll();
}
