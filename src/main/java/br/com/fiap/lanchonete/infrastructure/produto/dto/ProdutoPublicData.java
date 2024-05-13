package br.com.fiap.lanchonete.infrastructure.produto.dto;

import br.com.fiap.lanchonete.entity.produto.model.Categoria;
import br.com.fiap.lanchonete.entity.produto.model.Produto;
import br.com.fiap.lanchonete.usecase.produto.dto.IProdutoPublicData;

import java.math.BigDecimal;
import java.util.List;

public record ProdutoPublicData (
        Long id,
        String nome,
        Categoria categoria,
        BigDecimal preco,
        String descricao,
        List<String>imagens
) implements IProdutoPublicData {
    public ProdutoPublicData(Produto produto) {
        this(
                produto.getId(),
                produto.getNome(),
                produto.getCategoria(),
                produto.getPreco(),
                produto.getDescricao(),
                produto.getImagens()
        );
    }
}