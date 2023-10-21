package br.com.fiap.lanchonete.application.ports.input.usecase;

import br.com.fiap.lanchonete.adapters.driver.dto.ClienteDTO;
import org.springframework.stereotype.Component;

@Component
public interface GetClienteUseCase {

    ClienteDTO retrieveCliente(String cpf);
}
