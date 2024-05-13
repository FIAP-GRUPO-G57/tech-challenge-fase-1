package br.com.fiap.lanchonete.usecase.cliente.dto;

public interface IClientePublicData {
	Long id();
	String nome();
	String cpf();
	String telefone();
	String endereco();
	String email();
}
