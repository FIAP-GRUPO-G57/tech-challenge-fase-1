package br.com.fiap.lanchonete.application.ports.output;

import br.com.fiap.lanchonete.domain.entities.Cliente;

public interface ClienteOutputPort {

        Cliente save(Cliente cliente);

        Cliente findByCpf(String cpf);
}
