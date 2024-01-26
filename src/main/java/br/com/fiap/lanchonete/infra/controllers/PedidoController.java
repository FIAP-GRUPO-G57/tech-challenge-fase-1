package br.com.fiap.lanchonete.infra.controllers;

import br.com.fiap.lanchonete.core.domain.dto.ItemDto;
import br.com.fiap.lanchonete.core.domain.dto.PedidoDto;
import br.com.fiap.lanchonete.core.domain.dto.PedidoReduceDto;
import br.com.fiap.lanchonete.core.domain.entities.Item;
import br.com.fiap.lanchonete.core.domain.entities.Pedido;
import br.com.fiap.lanchonete.core.domain.mappers.ItemMapper;
import br.com.fiap.lanchonete.core.usecases.pedido.AddItemPedidoDbUsecase;
import br.com.fiap.lanchonete.core.usecases.pedido.CheckoutPedidoDbUsecase;
import br.com.fiap.lanchonete.core.usecases.pedido.ConfirmPedidoDbUsecase;
import br.com.fiap.lanchonete.core.usecases.pedido.CreatePedidoDbUsecase;
import br.com.fiap.lanchonete.core.usecases.pedido.DeleteItemPedidoDbUsecase;
import br.com.fiap.lanchonete.core.usecases.pedido.FindPedidoByStatusDbUsecase;
import br.com.fiap.lanchonete.core.usecases.pedido.GetPedidoByIdDbUsecase;
import br.com.fiap.lanchonete.core.usecases.pedido.PayPedidoDbUsecase;
import br.com.fiap.lanchonete.core.usecases.pedido.UpdatePedidoDbUsecase;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final GetPedidoByIdDbUsecase getPedidoByIdDbUsecase;

    private final FindPedidoByStatusDbUsecase findPedidoByStatusDbUsecase;

    private final CreatePedidoDbUsecase createPedidoDbUsecase;

    private final UpdatePedidoDbUsecase updatePedidoDbUsecase;

    private final AddItemPedidoDbUsecase addItemPedidoDbUsecase;

    private final DeleteItemPedidoDbUsecase deleteItemPedidoDbUsecase;

    private final CheckoutPedidoDbUsecase checkoutPedidoDbUsecase;

    private final ConfirmPedidoDbUsecase confirmPedidoDbUsecase;

    private final PayPedidoDbUsecase payPedidoDbUsecase;

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    public ItemMapper itemMapper;

    @GetMapping(value = "/{id}")
    public ResponseEntity<PedidoDto> get(@PathVariable(value = "id") Long id) {
        Pedido pedido = Optional.ofNullable(getPedidoByIdDbUsecase.get(id))
                .orElseThrow(() -> new EntityNotFoundException("Pedido nao encontrado para o id :: " + id));
        return ResponseEntity.ok().body(modelMapper.map(pedido, PedidoDto.class));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PedidoDto> put(@PathVariable Long id, @RequestBody PedidoDto PedidoDto) {
        Pedido pedido = updatePedidoDbUsecase.update(id, modelMapper.map(PedidoDto, Pedido.class));
        if (Objects.nonNull(pedido)) {
            return ResponseEntity.ok(modelMapper.map(pedido, PedidoDto.class));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<PedidoReduceDto>> search(@RequestParam(name = "status", required = false) List<String> status) {
        List<PedidoDto> pedidos = findPedidoByStatusDbUsecase.findByStatus(status).stream()
                .map(pedido -> modelMapper.map(pedido, PedidoDto.class))
                .toList();
        List<PedidoReduceDto> pp = pedidos.stream()
                .map(p -> PedidoReduceDto.builder()
                        .id(p.getId())
                        .status(p.getStatus())
                        .criacao(p.getCriacao())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(pp);
    }

    @PostMapping
    public ResponseEntity<PedidoDto> post(@Validated @RequestBody PedidoDto PedidoDto) {
        Pedido pedido = createPedidoDbUsecase.create(modelMapper.map(PedidoDto, Pedido.class));
        return new ResponseEntity<PedidoDto>(modelMapper.map(pedido, PedidoDto.class), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}/add-items")
    public ResponseEntity<PedidoDto> addItemsToPedido(@PathVariable Long id, @RequestBody List<ItemDto> itens) {
        Pedido pedido = getPedidoByIdDbUsecase.get(id);
        if (Objects.nonNull(pedido)) {
            List<Item> itensDomain = itemMapper.toDomain(itens);
            pedido = addItemPedidoDbUsecase.addItemPedido(id, itensDomain);
            return ResponseEntity.ok(modelMapper.map(pedido, PedidoDto.class));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{id}/delete-item/{idItem}")
    public ResponseEntity<PedidoDto> deleteItemFromPedido(@PathVariable Long id, @PathVariable Long idItem) {
        Pedido pedido = getPedidoByIdDbUsecase.get(id);
        if (Objects.nonNull(pedido)) {
        	deleteItemPedidoDbUsecase.deleteItemPedido(id, idItem);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping(value = "/{id}/checkout")
    public ResponseEntity<PedidoDto> checkoutPedido(@PathVariable Long id, @RequestParam(required = true) Long collector, @RequestParam(required = true) String pos) {
        Pedido pedido = Pedido.builder().id(id).collector(Long.valueOf(collector)).pos(pos).build();
        Pedido ped = checkoutPedidoDbUsecase.checkoutPedido(pedido);
        if (Objects.nonNull(ped)) {
            return ResponseEntity.ok(modelMapper.map(ped, PedidoDto.class));
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/notifications")
    public ResponseEntity<PedidoDto> notification(@RequestParam(name = "id") Long id, @RequestParam(name = "topic") String topic) {
        Pedido pedido = Pedido.builder().orderId(id).paymentId(id).build();
        if (("merchant_order").equals(topic) && Objects.nonNull(id)) {
            pedido = confirmPedidoDbUsecase.confirmPedido(pedido);
        }
        if (("payment").equals(topic) && Objects.nonNull(id)) {
            pedido = payPedidoDbUsecase.payPedido(pedido);
        }
        return ResponseEntity.ok(modelMapper.map(pedido, PedidoDto.class));
    }
}
