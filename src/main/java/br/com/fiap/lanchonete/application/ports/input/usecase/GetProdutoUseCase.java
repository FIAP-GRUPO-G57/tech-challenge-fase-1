package br.com.fiap.lanchonete.application.ports.input.usecase;

import br.com.fiap.lanchonete.domain.entities.Produto;

import java.util.List;

public interface GetProdutoUseCase {
    List<Produto> findByCategoria(String categoria);
}
