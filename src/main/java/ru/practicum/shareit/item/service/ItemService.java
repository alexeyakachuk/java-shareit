package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto create(Long userId, ItemDto newItem);

    ItemDto update(Long id, Long userId, ItemDto updatedItemDto);

    ItemDto find(Long id);

    List<ItemDto> findAllByOwner(Long ownerId);

    List<ItemDto> search(String text);

    void delete(Long id);
}
