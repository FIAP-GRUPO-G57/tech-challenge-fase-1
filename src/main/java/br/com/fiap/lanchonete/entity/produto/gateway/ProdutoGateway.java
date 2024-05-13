package br.com.fiap.lanchonete.entity.produto.gateway;

import br.com.fiap.lanchonete.entity.produto.model.Categoria;
import br.com.fiap.lanchonete.entity.produto.model.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoGateway {
    Optional<Produto> findById(Long id);
    List<Produto> findByCategoria(Categoria categoria);
    List<Produto> findAll();
    Produto save(Produto produto);
    Produto update(Produto customer);
    void delete(Long id);
}
