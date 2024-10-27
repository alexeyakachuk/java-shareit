package ru.practicum.shareit.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.storage.UserStorage;

@Slf4j
@Service
public class UserServiceImpl {
    private final UserStorage userStorage;

    public UserServiceImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public UserDto create(User newUser) {
        User user = userStorage.create(newUser);
        UserDto userDto = UserMapper.toUserDto(user);
        return userDto;
    }

    public UserDto find(Long id) {
        User user = userStorage.find(id);
        UserDto userDto = UserMapper.toUserDto(user);
        return userDto;
    }

    public void delete(long id) {
        userStorage.delete(id);
    }

    public UserDto update(User newUser) {
//        UserDto userDto = UserMapper.toUserDto(newUser);
//        return userDto;
//        User updateUser = userStorage.update(newUser);
//        UserDto userDto = UserMapper.toUserDto(updateUser);
//        return userDto;
        User updatedUser  = userStorage.update(newUser ); // Передаем новый объект в хранилище
        return UserMapper.toUserDto(updatedUser );
    }
}
