package br.com.fiap.lanchonete.adapters.driver.rest;

import br.com.fiap.lanchonete.adapters.driver.dto.PedidoDTO;
import br.com.fiap.lanchonete.application.ports.input.usecase.*;
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

    private final UpdatePedidoUseCase updatePedidoUseCase;

    @Autowired
    public ModelMapper modelMapper;

    @GetMapping(value = "/{id}")
    public ResponseEntity<PedidoDTO> get(@PathVariable(value="id") Long id) {
        Pedido pedido = Optional.ofNullable(getPedidoByIdUseCase.get(id))
                .orElseThrow(() -> new EntityNotFoundException("Pedido nao encontrado para o id :: " + id));
        return ResponseEntity.ok().body(modelMapper.map(pedido, PedidoDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> search(@RequestParam(name="status", required = false) String status) {
        Status statusDomain = Objects.nonNull(status)?Status.valueOf(status):null;
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

    @PutMapping(value = "/{id}")
    public ResponseEntity<PedidoDTO> put(@PathVariable Long id, @Validated @RequestBody PedidoDTO pedidoDTO) {
        Pedido pedido = updatePedidoUseCase.update(id, modelMapper.map(pedidoDTO, Pedido.class));
        if(Objects.nonNull(pedido)) {
            return ResponseEntity.ok(modelMapper.map(pedido, PedidoDTO.class));
        }
        return ResponseEntity.notFound().build();

    }
}
