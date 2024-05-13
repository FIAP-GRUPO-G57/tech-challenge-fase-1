package br.com.fiap.lanchonete.infrastructure.config.db.schema;

import br.com.fiap.lanchonete.entity.cliente.model.Cliente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cliente")
public class ClienteSchema {

    @Id @Column(name="id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nome")
    private String nome;

    @Column(name="cpf", unique = true)
    private String cpf;

    @Column(name="telefone")
    private String telefone;

    @Column(name="email")
    private String email;

    public ClienteSchema(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.cpf = cliente.getCpf();
        this.telefone = cliente.getTelefone();
        this.email = cliente.getEmail();
    }

    public Cliente toCliente() {
        return Cliente.builder()
                .id(this.getId())
                .nome(this.getNome())
                .cpf(this.getCpf())
                .telefone(this.getTelefone())
                .email(this.getEmail())
                .build();
    }
}
