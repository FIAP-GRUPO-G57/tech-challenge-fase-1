package br.com.fiap.lanchonete.adapters.driven.data;

import jakarta.persistence.*;
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
    private PedidoData pedido;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private ProdutoData produto;

    @Column(name="preco")
    private BigDecimal preco;
}
