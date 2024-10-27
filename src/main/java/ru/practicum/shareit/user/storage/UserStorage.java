package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.Map;

public interface UserStorage {
    User create(User user);

    User find(Long id);

    void delete(Long id);

    User update(User newUser);

    Map<Long, User> getUsers();
}
