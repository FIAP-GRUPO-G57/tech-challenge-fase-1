package br.com.fiap.lanchonete.adapters.driven.repository;

import br.com.fiap.lanchonete.adapters.driven.data.ProdutoData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends CrudRepository<ProdutoData, Long> {
    List<ProdutoData> findByCategoria(String categoria);
    List<ProdutoData> findAll();
}
