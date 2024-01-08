package br.com.fiap.lanchonete.data.mappers;

import br.com.fiap.lanchonete.domain.entities.Item;
import br.com.fiap.lanchonete.infra.db.schemas.ItemSchema;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemDataMapper {

	private final ModelMapper modelMapper;

	public ItemDataMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public Item toDomain(ItemSchema data) {
		return modelMapper.map(data, Item.class);
	}

	public ItemSchema toData(Item item) {
		return modelMapper.map(item, ItemSchema.class);
	}

	public List<Item> toDomain(List<ItemSchema> datas) {
		return datas.stream().map(this::toDomain).toList();
	}

	public List<ItemSchema> toData(List<Item> items) {
		return items.stream().map(this::toData).toList();
	}
	
}
