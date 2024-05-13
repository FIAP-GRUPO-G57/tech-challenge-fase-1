package br.com.fiap.lanchonete.usecase.produto;

import br.com.fiap.lanchonete.entity.produto.gateway.ProdutoGateway;
import br.com.fiap.lanchonete.entity.produto.model.Produto;
import br.com.fiap.lanchonete.usecase.produto.dto.IProdutoRegistrationData;
import org.springframework.stereotype.Service;

@Service
public class CreateProdutoUsecase {

    private final ProdutoGateway produtoGateway;

    public CreateProdutoUsecase(ProdutoGateway produtoGateway) {
        this.produtoGateway = produtoGateway;
    }

    public Produto execute(IProdutoRegistrationData data) {
        Produto produto = new Produto(data.id(), data.nome(), data.categoria(), data.preco(), data.descricao(), data.imagens());
        return produtoGateway.save(produto);
    }
}
