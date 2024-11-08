package ru.practicum.shareit.item.storage;


import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage {
    Item create(Item newItem);

    Item update(Long id, Item updatedItem);

    Item find(Long id);

    List<Item> findAllByOwner(Long ownerId);

    List<Item> search(String text);

    void delete(Long id);
}
