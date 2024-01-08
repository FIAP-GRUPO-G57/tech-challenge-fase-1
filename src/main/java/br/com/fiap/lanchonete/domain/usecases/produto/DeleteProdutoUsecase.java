package br.com.fiap.lanchonete.domain.usecases.produto;

import br.com.fiap.lanchonete.domain.entities.Produto;

public interface DeleteProdutoUsecase {
    void delete(Produto produto);
}
