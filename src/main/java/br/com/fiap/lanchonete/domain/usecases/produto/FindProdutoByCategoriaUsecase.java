package br.com.fiap.lanchonete.domain.usecases.produto;

import br.com.fiap.lanchonete.domain.entities.Produto;
import br.com.fiap.lanchonete.domain.enums.CategoriaEnum;

import java.util.List;

public interface FindProdutoByCategoriaUsecase {
    List<Produto> findByCategoria(CategoriaEnum categoria);
}
