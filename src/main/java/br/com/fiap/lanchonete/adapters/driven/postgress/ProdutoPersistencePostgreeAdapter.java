package br.com.fiap.lanchonete.adapters.driven.postgress;

import br.com.fiap.lanchonete.adapters.driven.data.ProdutoData;
import br.com.fiap.lanchonete.adapters.driven.repository.ProdutoRepository;
import br.com.fiap.lanchonete.application.ports.output.ProdutoOutputPort;
import br.com.fiap.lanchonete.domain.entities.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProdutoPersistencePostgreeAdapter implements ProdutoOutputPort {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    public ModelMapper modelMapper;

    @Override
    public Produto get(Long id) {
        return modelMapper.map(produtoRepository.findById(id).get(), Produto.class);
    }

    @Override
    public List<Produto> findAll() {
        return produtoRepository.findAll().stream()
                .map(produtoData-> modelMapper.map(produtoData, Produto.class))
                .toList();
    }

    @Override
    public List<Produto> findByCategoria(String categoria) {
        return produtoRepository.findByCategoria(categoria).stream()
                .map(produtoData-> modelMapper.map(produtoData, Produto.class))
                .toList();
    }

    @Override
    public Produto save(Produto produto) {
        return modelMapper.map(produtoRepository.save(modelMapper.map(produto, ProdutoData.class)), Produto.class);
    }

    @Override
    public void delete(Produto produto) {
        produtoRepository.delete(modelMapper.map(produto, ProdutoData.class));
    }
}
