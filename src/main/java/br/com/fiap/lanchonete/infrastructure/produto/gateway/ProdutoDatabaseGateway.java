package br.com.fiap.lanchonete.infrastructure.produto.gateway;

import br.com.fiap.lanchonete.entity.produto.gateway.ProdutoGateway;
import br.com.fiap.lanchonete.entity.produto.model.Categoria;
import br.com.fiap.lanchonete.entity.produto.model.Produto;
import br.com.fiap.lanchonete.infrastructure.cliente.dto.ClientePublicData;
import br.com.fiap.lanchonete.infrastructure.config.db.repository.ProdutoRepository;
import br.com.fiap.lanchonete.infrastructure.config.db.schema.ProdutoSchema;
import br.com.fiap.lanchonete.infrastructure.produto.dto.ProdutoPublicData;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProdutoDatabaseGateway implements ProdutoGateway {

    private final ProdutoRepository produtoRepository;

    public ProdutoDatabaseGateway(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public Optional<ProdutoPublicData> findById(Long id) {
        return new ProdutoPublicData(produtoRepository.findById(id));
    }

    @Override
    public List<Produto> findByCategoria(Categoria categoria) {
        return produtoRepository.findByCategoria(categoria);
    }

    @Override
    public List<Produto> findAll() {
        return produtoRepository.findAll().stream().map(ProdutoSchema::toProduto).toList();
    }

    @Override
    public Produto save(Produto produto) {        
        return produtoRepository.save(new ProdutoSchema(produto)).toProduto();
    }

    @Override
    public Produto update(Produto produto) {
        return produtoRepository.save(new ProdutoSchema(produto)).toProduto();
    }

    @Override
    public void delete(Long id) {
        produtoRepository.deleteById(id);
    }
}