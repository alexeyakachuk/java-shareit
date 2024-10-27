package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;

public interface ItemStorage {
    Item create(Item newItem);

    Item find(Long id);
}
