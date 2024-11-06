package ru.practicum.shareit.item.controller;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemServiceImpl;

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
        String header = request.getHeader("X-Sharer-User-Id");
        if (StringUtils.isEmpty(header)) {
            throw new ValidationException("Не передан userId");
        }
        long userId = Long.parseLong(header);

        return itemService.create(userId, newItem);
    }

    @PatchMapping("/{id}")
    public ItemDto update(@PathVariable Long id, HttpServletRequest request, @RequestBody Item updatedItem) {
        String header = request.getHeader("X-Sharer-User-Id");
        if (StringUtils.isEmpty(header)) {
            throw new ValidationException("Не передан userId");
        }
        long userId = Long.parseLong(header);
        return itemService.update(id, userId, updatedItem);
    }

    @GetMapping("/{id}")
    public ItemDto find(HttpServletRequest request, @PathVariable Long id) {
        String header = request.getHeader("X-Sharer-User-Id");
        if (StringUtils.isEmpty(header)) {
            throw new ValidationException("Не передан userId");
        }
        return itemService.find(id);
    }

    @GetMapping
    public List<ItemDto> findAll(HttpServletRequest request) {
        String header = request.getHeader("X-Sharer-User-Id");
        if (StringUtils.isEmpty(header)) {
            throw new ValidationException("Не передан userId");
        }
        long userId = Long.parseLong(header);
        return itemService.findAllByOwner(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam String text) {
        return itemService.search(text);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable long itemId) {
        itemService.delete(itemId);
    }
}
