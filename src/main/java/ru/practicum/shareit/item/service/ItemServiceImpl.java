package ru.practicum.shareit.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
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
        Map<Long, User> users = userStorage.getUsers();
        User owner = users.get(userId);
        newItem.setOwner(owner);
        // Проверка на наличие пользователя с указанным userId
        if (!users.containsKey(userId)) {
            throw new NotFoundException(String.format("Пользователь с id %d не найден", userId));
        }


        // Создание нового элемента
        Item item = itemStorage.create(newItem);
        ItemDto itemDto = ItemMapper.toItemDto(item);
        return itemDto;
    }

    public ItemDto find(long id) {
        Item item = itemStorage.find(id);
        ItemDto itemDto = ItemMapper.toItemDto(item);
        return itemDto;
    }
}
