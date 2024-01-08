package br.com.fiap.lanchonete.infra.controllers;

import br.com.fiap.lanchonete.domain.dto.ItemDto;
import br.com.fiap.lanchonete.domain.dto.PedidoDto;
import br.com.fiap.lanchonete.domain.entities.Item;
import br.com.fiap.lanchonete.domain.entities.Pedido;
import br.com.fiap.lanchonete.domain.enums.StatusEnum;
import br.com.fiap.lanchonete.domain.mappers.ItemMapper;
import br.com.fiap.lanchonete.domain.usecases.itemPedido.AddItemPedidoUsecase;
import br.com.fiap.lanchonete.domain.usecases.itemPedido.DeleteItemPedidoUsecase;
import br.com.fiap.lanchonete.domain.usecases.pedido.*;
import br.com.fiap.lanchonete.main.exception.EnumValidationException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

	private final GetPedidoByIdUsecase getPedidoByIdUsecase;

	private final FindPedidoByStatusUsecase getPedidoUsecase;

	private final CreatePedidoUsecase createPedidoUsecase;

	private final AddItemPedidoUsecase updatePedidoUsecase;

	private final DeleteItemPedidoUsecase deleteItemPedidoUsecase;

	private final CheckoutPedidoUsecase checkoutPedidoUsecase;

	@Autowired
	public ModelMapper modelMapper;

	@Autowired
	public ItemMapper itemMapper;

	@GetMapping(value = "/{id}")
	public ResponseEntity<PedidoDto> get(@PathVariable(value = "id") Long id) {
		Pedido pedido = Optional.ofNullable(getPedidoByIdUsecase.get(id))
				.orElseThrow(() -> new EntityNotFoundException("Pedido nao encontrado para o id :: " + id));
		return ResponseEntity.ok().body(modelMapper.map(pedido, PedidoDto.class));
	}

	@GetMapping
	public ResponseEntity<List<PedidoDto>> search(@RequestParam(name = "status", required = false) String status) {
		StatusEnum statusDomain = null;

		try {
			if (Objects.nonNull(status)) {
				statusDomain = StatusEnum.valueOf(status);
			}
		} catch (IllegalArgumentException e) {
			throw new EnumValidationException("Status ", status);
		}

		List<PedidoDto> pedidos = getPedidoUsecase.findByStatus(statusDomain).stream()
				.map(pedido -> modelMapper.map(pedido, PedidoDto.class)).toList();
		return ResponseEntity.ok(pedidos);
	}

	@PostMapping
	public ResponseEntity<PedidoDto> post(@Validated @RequestBody PedidoDto pedidoDTO) {
		Pedido pedido = createPedidoUsecase.create(modelMapper.map(pedidoDTO, Pedido.class));
		return new ResponseEntity<PedidoDto>(modelMapper.map(pedido, PedidoDto.class), HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}/add-items")
	public ResponseEntity<PedidoDto> addItemsToPedido(@PathVariable Long id, @RequestBody List<ItemDto> itens) {
		Pedido pedido = getPedidoByIdUsecase.get(id);

		if (Objects.nonNull(pedido)) {
			List<Item> itensDomain = itemMapper.toDomain(itens);
			pedido = updatePedidoUsecase.addItemPedido(id, itensDomain);
			return ResponseEntity.ok(modelMapper.map(pedido, PedidoDto.class));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping(value = "/{id}/delete-item/{idItem}")
	public ResponseEntity<PedidoDto> deleteItemFromPedido(@PathVariable Long id, @PathVariable Long idItem) {
		Pedido pedido = getPedidoByIdUsecase.get(id);

		if (Objects.nonNull(pedido)) {
			deleteItemPedidoUsecase.deleteItemPedido(id, idItem);
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.notFound().build();
	}

	@PatchMapping(value = "/{id}/checkout")
	public ResponseEntity<PedidoDto> checkoutPedido(@PathVariable Long id) {
		checkoutPedidoUsecase.checkoutPedido(id);
		return ResponseEntity.noContent().build();
	}
}
