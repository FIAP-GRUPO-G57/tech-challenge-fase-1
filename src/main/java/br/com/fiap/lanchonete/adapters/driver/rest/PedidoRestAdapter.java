package br.com.fiap.lanchonete.adapters.driver.rest;

import br.com.fiap.lanchonete.adapters.driver.dto.ItemDTO;
import br.com.fiap.lanchonete.adapters.driver.dto.PedidoDTO;
import br.com.fiap.lanchonete.adapters.driver.exception.EnumValidationException;
import br.com.fiap.lanchonete.adapters.driver.mappers.ItemMapper;
import br.com.fiap.lanchonete.application.ports.input.usecase.*;
import br.com.fiap.lanchonete.domain.entities.Item;
import br.com.fiap.lanchonete.domain.entities.Pedido;
import br.com.fiap.lanchonete.domain.vo.Status;
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
public class PedidoRestAdapter {

    private final GetPedidoByIdUseCase getPedidoByIdUseCase;

    private final GetPedidoUseCase getPedidoUseCase;

    private final CreatePedidoUseCase createPedidoUseCase;

    private final AddItemPedidoUseCase updatePedidoUseCase;


    private final DeleteItemPedidoUseCase deleteItemPedidoUseCase;

    private final CheckoutPedidoUseCase checkoutPedidoUseCase;

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    public ItemMapper itemMapper;

    @GetMapping(value = "/{id}")
    public ResponseEntity<PedidoDTO> get(@PathVariable(value="id") Long id) {
        Pedido pedido = Optional.ofNullable(getPedidoByIdUseCase.get(id))
                .orElseThrow(() -> new EntityNotFoundException("Pedido nao encontrado para o id :: " + id));
        return ResponseEntity.ok().body(modelMapper.map(pedido, PedidoDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> search(@RequestParam(name="status", required = false) String status) {

        Status statusDomain = null;
        try {
            if (Objects.nonNull(status)) {
                statusDomain = Status.valueOf(status);
            }
        } catch (IllegalArgumentException e) {

            throw new EnumValidationException("Status ",status);
        }


       // Status statusDomain = Objects.nonNull(status)?Status.valueOf(status):null;
        List<PedidoDTO> pedidos = getPedidoUseCase.findByStatus(statusDomain).stream()
                .map(pedido-> modelMapper.map(pedido, PedidoDTO.class))
                .toList();
        return ResponseEntity.ok(pedidos);
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> post(@Validated @RequestBody PedidoDTO pedidoDTO) {
        Pedido pedido =  createPedidoUseCase.create(modelMapper.map(pedidoDTO, Pedido.class));
        return new ResponseEntity(modelMapper.map(pedido, PedidoDTO.class), HttpStatus.CREATED);
    }



    @PutMapping(value = "/{id}/add-items")
    public ResponseEntity<PedidoDTO> addItemsToPedido(@PathVariable Long id, @RequestBody List<ItemDTO> itens) {
        Pedido pedido = getPedidoByIdUseCase.get(id);
           if(Objects.nonNull(pedido)) {
               // Mapp List<ItemDTO> to List<Item>
                List<Item> itensDomain = itemMapper.toDomain(itens);
                pedido = updatePedidoUseCase.addItemPedido(id, itensDomain);
                return ResponseEntity.ok(modelMapper.map(pedido, PedidoDTO.class));
            }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{id}/delete-item/{idItem}")
    public ResponseEntity<PedidoDTO> deleteItemFromPedido(@PathVariable Long id, @PathVariable Long idItem) {
        Pedido pedido = getPedidoByIdUseCase.get(id);
           if(Objects.nonNull(pedido)) {
               // Mapp List<ItemDTO> to List<Item>
               deleteItemPedidoUseCase.deleteItemPedido(id, idItem);
                return ResponseEntity.noContent().build();
            }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping(value = "/{id}/checkout")
          public ResponseEntity<PedidoDTO> checkoutPedido(@PathVariable Long id){


            checkoutPedidoUseCase.checkoutPedido(id);
            return ResponseEntity.noContent().build();

     }
}
