package br.com.fiap.lanchonete.application.usecase;

import br.com.fiap.lanchonete.adapters.driver.dto.ClienteDTO;
import org.springframework.stereotype.Component;

public interface CreateClienteUseCase {

    ClienteDTO createCliente(ClienteDTO cliente);
}
