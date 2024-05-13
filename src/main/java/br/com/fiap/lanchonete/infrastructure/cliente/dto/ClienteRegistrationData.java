package br.com.fiap.lanchonete.infrastructure.cliente.dto;

import br.com.fiap.lanchonete.usecase.cliente.dto.IClienteRegistrationData;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ClienteRegistrationData(
	Long id,
	String nome,
	@NotNull  @Pattern(regexp = "(\\d{3}).\\d{3}.\\d{3}-\\d{2}$", message = "CPF inválido")
	String cpf,
	@Pattern(regexp = "(\\d{2})\\d{5}-\\d{4}$", message = "Telefone inválido")
	String telefone,
	String endereco,
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "E-mail inválido")
	String email
) implements IClienteRegistrationData {}
