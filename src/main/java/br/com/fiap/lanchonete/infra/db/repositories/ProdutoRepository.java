package br.com.fiap.lanchonete.infra.db.repositories;

import br.com.fiap.lanchonete.core.domain.enums.CategoriaEnum;
import br.com.fiap.lanchonete.infra.db.entities.ProdutoEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends CrudRepository<ProdutoEntity, Long> {
    List<ProdutoEntity> findByCategoria(CategoriaEnum categoria);
    List<ProdutoEntity> findAll();
}
