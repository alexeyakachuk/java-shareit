package ru.practicum.shareit.item.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */

@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemServiceImpl itemService;

    public ItemController(ItemServiceImpl itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ItemDto create(HttpServletRequest request, @Valid @RequestBody Item newItem) {
        long userId = Long.parseLong(request.getHeader("X-Sharer-User-Id"));

        return itemService.create(userId, newItem);
    }

    @PatchMapping("/{id}")
    public ItemDto update(@PathVariable Long id, @RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody Item updatedItem) {
        return itemService.update(id, userId, updatedItem);
    }

    @GetMapping("/{id}")
    public ItemDto find(@PathVariable Long id) {
        return itemService.find(id);
    }

    @GetMapping
    public List<ItemDto> findAll(@RequestHeader("X-Sharer-User -Id") Long userId) {
        return itemService.findAllByOwner(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam String text) {
        return itemService.search(text);
    }
}
