package br.com.fiap.lanchonete.usecase.produto;

import br.com.fiap.lanchonete.entity.produto.gateway.ProdutoGateway;
import br.com.fiap.lanchonete.entity.produto.model.Categoria;
import br.com.fiap.lanchonete.entity.produto.model.Produto;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

public class SearchProdutoUsecase {
    private final ProdutoGateway produtoGateway;

    public SearchProdutoUsecase(ProdutoGateway produtoGateway) {
        this.produtoGateway = produtoGateway;
    }

    public List<Produto> execute(String categoria) {
        if(StringUtils.isNotEmpty(categoria)) {
            Categoria categ = Optional.ofNullable(categoria).map(Categoria::valueOf).orElse(null);
            return produtoGateway.findByCategoria(categ);
        }
        return produtoGateway.findAll();
    }
}
