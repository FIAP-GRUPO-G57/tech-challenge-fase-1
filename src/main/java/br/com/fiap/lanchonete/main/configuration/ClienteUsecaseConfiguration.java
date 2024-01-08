package br.com.fiap.lanchonete.main.configuration;

import br.com.fiap.lanchonete.data.usecases.cliente.CreateClienteDbUsecase;
import br.com.fiap.lanchonete.data.usecases.cliente.RetrieveClienteDbUsecase;
import br.com.fiap.lanchonete.domain.mappers.ClienteMapper;
import br.com.fiap.lanchonete.infra.db.repositories.postgresql.ClientePostgresqlRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClienteUsecaseConfiguration {

	@Autowired
	private ClientePostgresqlRepository clientePostgresqlRepository;

	@Autowired
	private ClienteMapper clienteMapper;

	@Bean
	CreateClienteDbUsecase constructCreateClienteUsecase() {
		return new CreateClienteDbUsecase(clientePostgresqlRepository, clientePostgresqlRepository, clienteMapper);
	}
	
	@Bean
	RetrieveClienteDbUsecase constructRetrieveClienteDbUsecase() {
		return new RetrieveClienteDbUsecase(clientePostgresqlRepository, clienteMapper);
	}
}
