package br.com.fiap.lanchonete.infrastructure.config.db.repository;

import br.com.fiap.lanchonete.entity.produto.model.Categoria;
import br.com.fiap.lanchonete.infrastructure.config.db.schema.ProdutoSchema;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends CrudRepository<ProdutoSchema, Long> {
	List<ProdutoSchema> findByCategoria(Categoria categoria);
	List<ProdutoSchema> findAll();
}
