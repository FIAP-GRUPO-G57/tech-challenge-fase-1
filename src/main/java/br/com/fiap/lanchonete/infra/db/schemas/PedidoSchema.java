package br.com.fiap.lanchonete.infra.db.schemas;

import br.com.fiap.lanchonete.domain.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="pedido")
public class PedidoSchema {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteSchema cliente;

    @OneToMany(mappedBy="pedido", cascade = {CascadeType.PERSIST})
    private List<ItemSchema> itens;

    @Column(name="preco")
    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private LocalDateTime criacao;

    private LocalDateTime alteracao;


    @Override
    public String toString() {
        return "PedidoData [id=" + id + ", cliente=" + cliente + ", itens=" + itens + ", preco=" + preco + ", status="
                + status + ", criacao=" + criacao + ", alteracao=" + alteracao + "]";
    }
}
