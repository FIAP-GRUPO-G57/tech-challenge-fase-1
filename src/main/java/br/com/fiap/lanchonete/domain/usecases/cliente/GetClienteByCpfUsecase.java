package br.com.fiap.lanchonete.domain.usecases.cliente;

import br.com.fiap.lanchonete.domain.dto.ClienteDto;

public interface GetClienteByCpfUsecase {
    ClienteDto retrieveCliente(String cpf);
}
