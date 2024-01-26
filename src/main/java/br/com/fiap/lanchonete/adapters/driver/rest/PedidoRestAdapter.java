package br.com.fiap.lanchonete.adapters.driver.rest;

import br.com.fiap.lanchonete.adapters.driver.dto.ItemDTO;
import br.com.fiap.lanchonete.adapters.driver.dto.PedidoDTO;
import br.com.fiap.lanchonete.adapters.driver.dto.PedidoReduceDTO;
import br.com.fiap.lanchonete.adapters.driver.mappers.ItemMapper;
import br.com.fiap.lanchonete.application.ports.input.usecase.*;
import br.com.fiap.lanchonete.domain.entities.Item;
import br.com.fiap.lanchonete.domain.entities.Pedido;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class PedidoRestAdapter {

    private final GetPedidoByIdUseCase getPedidoByIdUseCase;

    private final GetPedidoUseCase getPedidoUseCase;

    private final CreatePedidoUseCase createPedidoUseCase;

    private final UpdatePedidoUseCase updatePedidoUseCase;

    private final AddItemPedidoUseCase addItemPedidoUseCase;

    private final DeleteItemPedidoUseCase deleteItemPedidoUseCase;

    private final CheckoutPedidoUseCase checkoutPedidoUseCase;

    private final ConfirmPedidoUseCase confirmPedidoUseCase;

    private final PayPedidoUseCase payPedidoUseCase;

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    public ItemMapper itemMapper;

    @GetMapping(value = "/{id}")
    public ResponseEntity<PedidoDTO> get(@PathVariable(value = "id") Long id) {
        Pedido pedido = Optional.ofNullable(getPedidoByIdUseCase.get(id))
                .orElseThrow(() -> new EntityNotFoundException("Pedido nao encontrado para o id :: " + id));
        return ResponseEntity.ok().body(modelMapper.map(pedido, PedidoDTO.class));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PedidoDTO> put(@PathVariable Long id, @RequestBody PedidoDTO pedidoDTO) {
        Pedido pedido = updatePedidoUseCase.update(id, modelMapper.map(pedidoDTO, Pedido.class));
        if (Objects.nonNull(pedido)) {
            return ResponseEntity.ok(modelMapper.map(pedido, PedidoDTO.class));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<PedidoReduceDTO>> search(@RequestParam(name = "status", required = false) List<String> status) {
        List<PedidoDTO> pedidos = getPedidoUseCase.findByStatus(status).stream()
                .map(pedido -> modelMapper.map(pedido, PedidoDTO.class))
                .toList();
        List<PedidoReduceDTO> pp = pedidos.stream()
                .map(p -> PedidoReduceDTO.builder()
                        .id(p.getId())
                        .status(p.getStatus())
                        .criacao(p.getCriacao())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(pp);
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> post(@Validated @RequestBody PedidoDTO pedidoDTO) {
        Pedido pedido = createPedidoUseCase.create(modelMapper.map(pedidoDTO, Pedido.class));
        return new ResponseEntity(modelMapper.map(pedido, PedidoDTO.class), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}/add-items")
    public ResponseEntity<PedidoDTO> addItemsToPedido(@PathVariable Long id, @RequestBody List<ItemDTO> itens) {
        Pedido pedido = getPedidoByIdUseCase.get(id);
        if (Objects.nonNull(pedido)) {
            List<Item> itensDomain = itemMapper.toDomain(itens);
            pedido = addItemPedidoUseCase.addItemPedido(id, itensDomain);
            return ResponseEntity.ok(modelMapper.map(pedido, PedidoDTO.class));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{id}/delete-item/{idItem}")
    public ResponseEntity<PedidoDTO> deleteItemFromPedido(@PathVariable Long id, @PathVariable Long idItem) {
        Pedido pedido = getPedidoByIdUseCase.get(id);
        if (Objects.nonNull(pedido)) {
            deleteItemPedidoUseCase.deleteItemPedido(id, idItem);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping(value = "/{id}/checkout")
    public ResponseEntity<PedidoDTO> checkoutPedido(@PathVariable Long id, @RequestParam(required = true) Long collector, @RequestParam(required = true) String pos) {
        Pedido pedido = Pedido.builder().id(id).collector(Long.valueOf(collector)).pos(pos).build();
        Pedido ped = checkoutPedidoUseCase.checkoutPedido(pedido);
        if (Objects.nonNull(ped)) {
            return ResponseEntity.ok(modelMapper.map(ped, PedidoDTO.class));
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/notifications")
    public ResponseEntity<PedidoDTO> notification(@RequestParam(name = "id") Long id, @RequestParam(name = "topic") String topic) {
        Pedido pedido = Pedido.builder().orderId(id).paymentId(id).build();
        log.info("Pedido: {}", pedido);
        if (("merchant_order").equals(topic) && Objects.nonNull(id)) {
            pedido = confirmPedidoUseCase.confirmPedido(pedido);
        }
        if (("payment").equals(topic) && Objects.nonNull(id)) {
            pedido = payPedidoUseCase.payPedido(pedido);
        }
        return ResponseEntity.ok(modelMapper.map(pedido, PedidoDTO.class));
    }
}
