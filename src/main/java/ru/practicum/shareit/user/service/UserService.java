package ru.practicum.shareit.user.service;

import jakarta.validation.Valid;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

public class UserService {
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public UserDto create(@Valid User newUser) {
        User user = userStorage.create(newUser);
        UserDto userDto = UserMapper.toUserDto(user);
        return userDto;
    }

    public UserDto find(long id) {
        User user = userStorage.find(id);
        UserDto userDto = UserMapper.toUserDto(user);
        return userDto;
    }

    public void delete(long id) {
        userStorage.delete(id);
    }
}
