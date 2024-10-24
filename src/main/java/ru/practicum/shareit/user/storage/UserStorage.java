package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

public interface UserStorage {
    User create(User user);

    User find(long id);

    void delete(long id);

    User update(User newUser);
}
