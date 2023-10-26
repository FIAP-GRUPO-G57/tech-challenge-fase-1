package br.com.fiap.lanchonete.application.ports.input.usecase;

import br.com.fiap.lanchonete.domain.entities.Produto;

public interface GetProdutoByIdUseCase {
    Produto get(Long id);
}
