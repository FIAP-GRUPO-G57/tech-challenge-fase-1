package br.com.fiap.lanchonete.adapters.driven.repository;

import br.com.fiap.lanchonete.adapters.driven.data.ClienteData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends CrudRepository<ClienteData, Long> {
    ClienteData findByCpf(String cpf);
}
