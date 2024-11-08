package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

public interface UserService {

    UserDto create(User newUser);

    UserDto find(Long id);

    void delete(Long id);

    UserDto update(User newUser);
}
