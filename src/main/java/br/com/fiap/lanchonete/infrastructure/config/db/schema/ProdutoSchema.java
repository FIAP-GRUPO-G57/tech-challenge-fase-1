package br.com.fiap.lanchonete.infrastructure.config.db.schema;

import br.com.fiap.lanchonete.entity.produto.model.Categoria;
import br.com.fiap.lanchonete.entity.produto.model.Produto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cliente")
public class ProdutoSchema {

    @Id @Column(name="id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nome")
    private String nome;

    @Enumerated(EnumType.STRING) @Column(name = "categoria", nullable = false)
    private Categoria categoria;

    @Column(name="preco", nullable = false)
    private BigDecimal preco;

    @Column(name="descricao", nullable = false)
    private String descricao;

    @Column(name="imagens")
    private List<String> imagens;

    public ProdutoSchema(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.categoria = produto.getCategoria();
        this.preco = produto.getPreco();
        this.descricao = produto.getDescricao();
        this.imagens = produto.getImagens();
    }

    public Produto toProduto() {
        return Produto.builder()
                .id(this.getId())
                .nome(this.getNome())
                .categoria(this.getCategoria())
                .preco(this.getPreco())
                .descricao(this.getDescricao())
                .imagens(this.getImagens())
                .build();
    }
}
