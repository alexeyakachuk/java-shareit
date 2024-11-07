package ru.practicum.shareit.item.storage;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.model.Item;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@Getter
public class ItemInMemoryStorage implements ItemStorage {
    private final Map<Long, Item> items = new HashMap<>();
    private static Long id = 0L;

    @Override
    public Item create(Item item) {
        if (item.getName() == null || item.getName().isEmpty()) {
            throw new ValidationException("Имя вещи не может быть пустым");
        }
        if (item.getOwner() == null) {
            throw new NotFoundException("У вещи должен быть владелиц");
        }
        if (item.getDescription() == null) {
            throw new ValidationException("У вещи должно быть описание");
        }
        if (item.getAvailable() == null) {
            throw new ValidationException("");
        }

        long nextId = getNextId();
        item.setId(nextId);
        items.put(item.getId(), item);
        log.info("Создана вещь с id {}", nextId);
        return item;
    }

    @Override
    public Item update(Long id, Item updatedItem) {
        if (!items.containsKey(id)) {
            throw new NotFoundException("Вещь с id " + id + " не найдена");
        }

        Item existingItem = items.get(id);

        // Обновляем только поля, которые были переданы в updatedItem
        if (updatedItem.getName() != null && !updatedItem.getName().isEmpty()) {
            existingItem.setName(updatedItem.getName());
        }
        if (updatedItem.getDescription() != null) {
            existingItem.setDescription(updatedItem.getDescription());
        }
        if (updatedItem.getAvailable() != existingItem.getAvailable()) {
            existingItem.setAvailable(updatedItem.getAvailable());
        }

        return existingItem;
    }

    @Override
    public Item find(Long id) {
        if (!items.containsKey(id)) {
            throw new NotFoundException("Вещь с id " + id + " не найдена");
        }
        return items.get(id);
    }

    @Override
    public List<Item> findAllByOwner(Long ownerId) {
        return items.values().stream()
                .filter(item -> item.getOwner().getId().equals(ownerId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> search(String text) {
        if (text == null || text.isBlank()) {
            return Collections.emptyList();
        }
        return items.values().stream()
                .filter(item -> item.getAvailable() != null && item.getAvailable() &&
                        (item.getName().toLowerCase().contains(text.toLowerCase()) ||
                                item.getDescription().toLowerCase().contains(text.toLowerCase())))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        items.remove(id);
    }

    private long getNextId() {
        return id++;
    }
}
