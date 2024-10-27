package ru.practicum.shareit.item.storage;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@Getter
public class ItemInMemoryStorage implements ItemStorage{
    private final Map<Long, Item> items = new HashMap<>();

    @Override
    public Item create(Item item) {
        String name = item.getName();
        if (name.isEmpty()) {
            throw new ValidationException("Не корректно заполнены данные");
        }

        long nextId = getNextId();
        item.setId(nextId);
        items.put(item.getId(), item);



        return item;
    }

    @Override
    public Item find(Long id) {
//        if (!items.containsKey(id)) {
//            throw new NotFoundException("Item с таким id " + id + " нет");
//        }
        return items.get(id);
    }

    private long getNextId() {
        long currentMaxId = items.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
