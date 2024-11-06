package ru.practicum.shareit.item.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    public ItemServiceImpl(ItemStorage itemStorage, UserStorage userStorage) {
        this.itemStorage = itemStorage;
        this.userStorage = userStorage;
    }

    @Override
    public ItemDto create(Long userId, Item newItem) {
        User owner = userStorage.find(userId);
        newItem.setOwner(owner);
        Item item = itemStorage.create(newItem);
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto update(Long id, Long userId, Item updatedItem) {

        Item item = itemStorage.find(id);

        if (!item.getOwner().getId().equals(userId)) {
            throw new NotFoundException("Только владелец может обновить вещь");
        }

        Item updatedItemInStorage = itemStorage.update(id, updatedItem);

        return ItemMapper.toItemDto(updatedItemInStorage);
    }

    @Override
    public ItemDto find(Long id) {
        Item item = itemStorage.find(id);
        return ItemMapper.toItemDto(item);
    }

    @Override
    public List<ItemDto> findAllByOwner(Long ownerId) {
        return itemStorage.findAllByOwner(ownerId).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> search(String text) {
        return itemStorage.search(text).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        itemStorage.delete(id);
    }
}
