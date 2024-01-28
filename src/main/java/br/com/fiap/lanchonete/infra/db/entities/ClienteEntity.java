package br.com.fiap.lanchonete.infra.db.entities;

import jakarta.persistence.*;


@Entity
@Table(name="cliente")
public class ClienteEntity {

    //Incluir os atributos da classe ClienteData conforme a classe cliente do domínio
    //Incluir os métodos getters e setters

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="nome")
    private String nome;

    @Column(name="cpf", unique = true)
    private String cpf;

    @Column(name="telefone")
    private String telefone;

    @Column(name="email")
    private String email;


    public ClienteEntity(Long id, String nome, String cpf, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    public ClienteEntity() {

    }

    public Long getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getCpf() {
        return cpf;
    }
    public String getTelefone() {
        return telefone;
    }
    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
