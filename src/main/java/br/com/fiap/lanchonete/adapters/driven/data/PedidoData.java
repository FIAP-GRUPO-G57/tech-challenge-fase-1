package br.com.fiap.lanchonete.adapters.driven.data;

import br.com.fiap.lanchonete.adapters.driven.enums.Status;
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
public class PedidoData {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteData cliente;

    @OneToMany(mappedBy="pedido", cascade = {CascadeType.PERSIST})
    private List<ItemData> itens;

    @Column(name="preco")
    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime criacao;

    private LocalDateTime alteracao;


    @Override
    public String toString() {
        return "PedidoData [id=" + id + ", cliente=" + cliente + ", itens=" + itens + ", preco=" + preco + ", status="
                + status + ", criacao=" + criacao + ", alteracao=" + alteracao + "]";
    }
}
