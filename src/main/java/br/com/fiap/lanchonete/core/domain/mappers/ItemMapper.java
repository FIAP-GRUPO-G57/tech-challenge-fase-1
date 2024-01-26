package br.com.fiap.lanchonete.core.domain.mappers;

import br.com.fiap.lanchonete.core.domain.dto.ItemDto;
import br.com.fiap.lanchonete.core.domain.entities.Item;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemMapper {

	private final ModelMapper modelMapper;

	public ItemMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public Item toDomain(ItemDto dto) {
		return modelMapper.map(dto, br.com.fiap.lanchonete.core.domain.entities.Item.class);
	}

	public ItemDto toDTO(Item item) {
		return modelMapper.map(item, ItemDto.class);
	}

	public List<Item> toDomain(List<ItemDto> dtos) {
		return dtos.stream().map(this::toDomain).toList();
	}

	public List<ItemDto> toDTO(List<Item> items) {
		return items.stream().map(this::toDTO).toList();
	}
	
}
