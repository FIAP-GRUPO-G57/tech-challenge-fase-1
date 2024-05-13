package br.com.fiap.lanchonete.usecase.produto;

import br.com.fiap.lanchonete.entity.produto.exception.ProdutoNotFoundException;
import br.com.fiap.lanchonete.entity.produto.gateway.ProdutoGateway;
import br.com.fiap.lanchonete.entity.produto.model.Produto;
import br.com.fiap.lanchonete.usecase.produto.dto.IProdutoUpdateData;
import org.springframework.stereotype.Service;

@Service
public class UpdateProdutoUsecase {

    private final ProdutoGateway produtoGateway;

    public UpdateProdutoUsecase(ProdutoGateway produtoGateway) {
        this.produtoGateway = produtoGateway;
    }

    public Produto execute(Long id, IProdutoUpdateData dados) throws ProdutoNotFoundException {
        Produto produto = this.produtoGateway.findById(id)
                .orElseThrow(ProdutoNotFoundException::new);
        if(dados.preco() != null)
            produto.setPreco(dados.preco());
        return this.produtoGateway.update(produto);
    }
}
