package br.com.fiap.lanchonete.application.ports.output;

import br.com.fiap.lanchonete.domain.entities.Produto;
import br.com.fiap.lanchonete.domain.vo.Categoria;

import java.util.List;

public interface ProdutoOutputPort {

        Produto get(Long id);

        List<Produto> findAll();

        List<Produto> findByCategoria(Categoria categoria);

        Produto save(Produto produto);

        void delete(Produto produto);
}
