package ru.practicum.shareit.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service
public class ItemServiceImpl {
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    public ItemServiceImpl(ItemStorage itemStorage, UserStorage userStorage) {
        this.itemStorage = itemStorage;
        this.userStorage = userStorage;
    }

    public ItemDto create(Long userId, Item newItem) {
        User owner = userStorage.find(userId);
        newItem.setOwner(owner);
        Item item = itemStorage.create(newItem);
        return ItemMapper.toItemDto(item);
    }

    public ItemDto update(Long id, Long userId, Item updatedItem) {
        // Проверяем, существует ли предмет
        Item item = itemStorage.find(id);

        // Проверяем, является ли пользователь владельцем
        if (!item.getOwner().getId().equals(userId)) {
            throw new ValidationException("Только владелец может обновить вещь");
        }

        // Передаем обновленный предмет в хранилище
        Item updatedItemInStorage = itemStorage.update(id, updatedItem);

        // Возвращаем обновленный предмет в виде DTO
        return ItemMapper.toItemDto(updatedItemInStorage);
    }

    public ItemDto find(long id) {
        Item item = itemStorage.find(id);
        return ItemMapper.toItemDto(item);
    }

    public List<ItemDto> findAllByOwner(Long ownerId) {
        return itemStorage.findAllByOwner(ownerId).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    public List<ItemDto> search(String text) {
        return itemStorage.search(text).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }
}
