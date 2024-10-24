package ru.practicum.shareit.user.storage;

import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserInMemoryStorage implements UserStorage{
    private final Map<Long, User> users = new HashMap<>();

    @Override
    public User create(User user) {
        long nextId = getNextId();
        user.setId(nextId);
        users.put(user.getId(), user);
        log.info("Создан пользователь с id {}", nextId);

        return user;
    }

    @Override
    public User find(long id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("Пользователя с таким id " + id + " нет");
        }
        return users.get(id);
    }

    @Override
    public void delete(long id) {

    }

    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
