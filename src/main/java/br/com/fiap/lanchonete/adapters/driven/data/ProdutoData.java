package br.com.fiap.lanchonete.adapters.driven.data;

import br.com.fiap.lanchonete.adapters.driven.enums.Categoria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="produto")
public class ProdutoData {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nome", nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private Categoria categoria;

    @Column(name="preco", nullable = false)
    private BigDecimal preco;

    @Column(name="descricao", nullable = false)
    private String descricao;

    @Column(name="imagens")
    private List<String> imagens;
}
