package br.com.fiap.lanchonete.domain.usecases.cliente;

import java.util.List;

import br.com.fiap.lanchonete.domain.dto.ClienteDto;

public interface GetClienteUsecase {
	List<ClienteDto> getAll();
}
