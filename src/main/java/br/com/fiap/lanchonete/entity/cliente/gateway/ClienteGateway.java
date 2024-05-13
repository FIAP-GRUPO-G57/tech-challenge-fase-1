package br.com.fiap.lanchonete.entity.cliente.gateway;

import br.com.fiap.lanchonete.entity.cliente.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteGateway {
    Cliente save(Cliente cliente);
    Optional<Cliente> findByCpf(String cpf);
    Optional<Cliente> findById(Long id);
    List<Cliente> findAll();
}
