package br.com.fiap.lanchonete.adapters.driver.mappers;

import br.com.fiap.lanchonete.adapters.driver.dto.ItemDTO;
import br.com.fiap.lanchonete.domain.entities.Item;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemMapper {

    private final ModelMapper modelMapper;

    public ItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Item toDomain(ItemDTO dto) {
        return modelMapper.map(dto, br.com.fiap.lanchonete.domain.entities.Item.class);
    }

    public ItemDTO toDTO(Item item) {
        return modelMapper.map(item, ItemDTO.class);
    }

    public List<Item> toDomain(List<ItemDTO> dtos) {
        return dtos.stream().map(this::toDomain).toList();
    }

    public List<ItemDTO> toDTO(List<Item> items) {
        return items.stream().map(this::toDTO).toList();
    }
}
