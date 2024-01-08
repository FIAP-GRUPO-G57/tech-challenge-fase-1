package br.com.fiap.lanchonete.data.protocols.db.produto;

import java.util.List;

import br.com.fiap.lanchonete.domain.entities.Produto;
import br.com.fiap.lanchonete.domain.enums.CategoriaEnum;

public interface FindProdutoByCategoriaRepository {
	List<Produto> findByCategoria(CategoriaEnum categoria);
}