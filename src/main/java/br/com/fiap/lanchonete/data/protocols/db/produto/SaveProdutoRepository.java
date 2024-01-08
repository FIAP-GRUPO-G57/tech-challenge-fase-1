package br.com.fiap.lanchonete.data.protocols.db.produto;

import br.com.fiap.lanchonete.domain.entities.Produto;

public interface SaveProdutoRepository {
	Produto save(Produto produto);
}