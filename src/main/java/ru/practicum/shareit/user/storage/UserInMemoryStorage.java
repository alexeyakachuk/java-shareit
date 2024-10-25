package ru.practicum.shareit.user.storage;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.practicum.shareit.exception.DuplicateException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@Getter
public class UserInMemoryStorage implements UserStorage{
    private final Map<Long, User> users = new HashMap<>();
    private final Set<String> emails = new HashSet<>();

    @Override
    public User create(User user) {

        if (emails.contains(user.getEmail())) {
            throw new DuplicateException("Такой email уже есть");
        }

        long nextId = getNextId();
        user.setId(nextId);
        users.put(user.getId(), user);
        emails.add(user.getEmail());
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
        users.remove(id);
        log.info("Создан id {} удален ", id);

    }

    @Override
    public User update(@Valid @RequestBody User newUser) {
        checkId(newUser);

        Long id = newUser.getId();
        User oldUser = users.get(id);
        updateFields(oldUser , newUser );
        users.put(id, oldUser);
        return oldUser;
    }

    private void updateFields(User oldUser, User newUser) {
        if (newUser.getName() != null) {
            oldUser.setName(newUser.getName());
        }
        if (newUser.getEmail() != null) {
            oldUser.setEmail(newUser.getEmail());
        }

    }

    private void checkId(User newUser) {
        if (newUser.getId() == null) {
            throw new ValidationException("Id пользователя должен быть указан");
        }
        if (!users.containsKey(newUser.getId())) {
            throw new NotFoundException("Пользователя с таким id " + newUser.getId() + " нет");
        }
        if (emails.contains(newUser.getEmail())) {
            throw new DuplicateException("Такой email уже есть");
        }
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
