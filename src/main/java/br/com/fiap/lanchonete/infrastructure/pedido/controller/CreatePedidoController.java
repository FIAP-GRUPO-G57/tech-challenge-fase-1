package br.com.fiap.lanchonete.infrastructure.pedido.controller;

import br.com.fiap.lanchonete.infrastructure.produto.dto.ProdutoPublicData;
import br.com.fiap.lanchonete.usecase.pedido.CreatePedidoUsecase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreatePedidoController {

	private final CreatePedidoUsecase createPedidoUsecase;

	public CreatePedidoController(CreatePedidoUsecase createPedidoUsecase) {
		this.creatPedidoUsecase = createPedidoUsecase;
	}

	@PostMapping("/pedidos")
	public ResponseEntity<PedidoPublicData> createPedido(@Valid @RequestBody PedidoRegistrationData pedido) {
		return ResponseEntity.ok(new PedidoPublicData(createPedidoUsecase.execute(pedido)));
	}
}
