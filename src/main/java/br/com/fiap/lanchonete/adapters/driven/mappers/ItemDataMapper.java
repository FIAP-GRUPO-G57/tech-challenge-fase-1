package br.com.fiap.lanchonete.adapters.driven.mappers;

import br.com.fiap.lanchonete.adapters.driven.data.ItemData;
import br.com.fiap.lanchonete.domain.entities.Item;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemDataMapper {

    private final ModelMapper modelMapper;

    public ItemDataMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Item toDomain(ItemData data) {
        return modelMapper.map(data, Item.class);
    }

    public ItemData toData(Item item) {
        return modelMapper.map(item, ItemData.class);
    }

    public List<Item> toDomain(List<ItemData> datas) {
        return datas.stream().map(this::toDomain).toList();
    }

    public List<ItemData> toData(List<Item> items) {
        return items.stream().map(this::toData).toList();
    }
}
