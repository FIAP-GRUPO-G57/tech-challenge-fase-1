package br.com.fiap.lanchonete.data.protocols.db.produto;

import java.util.List;

import br.com.fiap.lanchonete.domain.entities.Produto;

public interface FindAllProdutosRepository {
	List<Produto> findAll();
}