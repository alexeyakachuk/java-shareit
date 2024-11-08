package ru.practicum.shareit.item.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemServiceImpl;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemServiceImpl itemService;

    public ItemController(ItemServiceImpl itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") long userId, @RequestBody ItemDto newItemDto) {
        return itemService.create(userId, newItemDto);
    }

    @PatchMapping("/{id}")
    public ItemDto update(@PathVariable Long id, @RequestHeader("X-Sharer-User-Id") long userId,
                          @RequestBody ItemDto updatedItemDto) {
        return itemService.update(id, userId, updatedItemDto);
    }

    @GetMapping("/{id}")
    public ItemDto find(@PathVariable Long id) {
        return itemService.find(id);
    }

    @GetMapping
    public List<ItemDto> findAll(@RequestHeader("X-Sharer-User-Id") long userId) {
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
