package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    ItemDto create(Long userId, Item newItem);

    ItemDto update(Long id, Long userId, Item updatedItem);

    ItemDto find(Long id);

    List<ItemDto> findAllByOwner(Long ownerId);

    List<ItemDto> search(String text);

    void delete(Long id);
}
