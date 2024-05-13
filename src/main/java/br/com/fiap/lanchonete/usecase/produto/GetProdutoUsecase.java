package br.com.fiap.lanchonete.usecase.produto;

import br.com.fiap.lanchonete.entity.produto.exception.ProdutoNotFoundException;
import br.com.fiap.lanchonete.entity.produto.gateway.ProdutoGateway;
import br.com.fiap.lanchonete.entity.produto.model.Produto;
import org.springframework.stereotype.Service;

@Service
public class GetProdutoUsecase {

    private final ProdutoGateway produtoGateway;

    public GetProdutoUsecase(ProdutoGateway produtoGateway) {
        this.produtoGateway = produtoGateway;
    }

    public Produto execute(Long id) throws ProdutoNotFoundException {
        return produtoGateway.findById(id).orElseThrow(ProdutoNotFoundException::new);
    }
}
