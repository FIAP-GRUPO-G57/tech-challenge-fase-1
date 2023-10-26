package br.com.fiap.lanchonete.application.ports.input.usecase;

import br.com.fiap.lanchonete.domain.entities.Produto;

public interface UpdateProdutoUseCase {
    Produto update(Long id, Produto produto);
}
