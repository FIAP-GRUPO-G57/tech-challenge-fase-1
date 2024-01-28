package br.com.fiap.lanchonete.core.usecases.ports.repositories;

import java.util.List;

import br.com.fiap.lanchonete.core.domain.entities.Produto;
import br.com.fiap.lanchonete.core.domain.enums.CategoriaEnum;

public interface ProdutoRepositoryPort {
	void delete(Produto produto);

	List<Produto> findAll();

	List<Produto> findByCategoria(CategoriaEnum categoria);

	Produto get(Long id);

	Produto save(Produto produto);
}
