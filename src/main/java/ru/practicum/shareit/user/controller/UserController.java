package ru.practicum.shareit.user.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto create(@Valid @RequestBody User newUser) {
        return userService.create(newUser);
    }

    @GetMapping("/{id}")
    public UserDto find(@PathVariable long id) {
        return userService.find(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.delete(id);
    }

    //    @PatchMapping
//    public UserDto update(User newUser) {
//        return userService.update(newUser);
//    }
    @PatchMapping("/{id}")
    public UserDto update(@PathVariable long id, @RequestBody User newUser) { // убрал @Valid
        newUser.setId(id);

        return userService.update(newUser);
    }
}
