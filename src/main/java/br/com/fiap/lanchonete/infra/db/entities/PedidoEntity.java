package br.com.fiap.lanchonete.infra.db.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.fiap.lanchonete.core.domain.enums.StatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="pedido")
public class PedidoEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;

    @OneToMany(mappedBy="pedido", cascade = {CascadeType.PERSIST})
    private List<ItemEntity> itens;

    @Column(name="preco")
    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Enumerated(EnumType.ORDINAL)
    private StatusEnum steps;

    @Column(name="qr_Data")
    private String qrData;

    @Column(name="external_reference")
    private Long externalReference;

    @Column(name="payment_id")
    private Long paymentId;

    @Enumerated(EnumType.STRING)
    private StatusEnum statusPagamento;

    @CreationTimestamp
    private LocalDateTime criacao;

    @UpdateTimestamp
    private LocalDateTime alteracao;

    @Override
    public String toString() {
        return "PedidoData [id=" + id + ", cliente=" + cliente + ", itens=" + itens + ", preco=" + preco + ", status="
                + status + ", criacao=" + criacao + ", alteracao=" + alteracao + "]";
    }
}
