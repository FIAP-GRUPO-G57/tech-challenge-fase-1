package br.com.fiap.lanchonete.adapters.driven.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="item")
public class ItemData {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @NotNull
    private PedidoData pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    @NotNull
    private ProdutoData produto;

    @Column(name="preco")
    @NotNull
    private BigDecimal preco;

    @Override
    public String toString() {
        return "ItemData [id=" + id + ", pedido=" + pedido + ", produto=" + produto + ", preco=" + preco + "]";
    }
}
