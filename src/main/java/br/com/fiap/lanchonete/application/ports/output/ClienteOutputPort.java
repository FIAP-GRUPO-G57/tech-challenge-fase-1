package br.com.fiap.lanchonete.application.ports.output;

import br.com.fiap.lanchonete.adapters.driver.dto.ClienteDTO;
import br.com.fiap.lanchonete.domain.entities.Cliente;

import java.util.List;

public interface ClienteOutputPort {

    Cliente save(Cliente cliente);

    Cliente findByCpf(String cpf);

    Cliente get(Long id);

    List<Cliente> findAll();
}
