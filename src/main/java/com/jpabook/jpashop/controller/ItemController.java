package com.jpabook.jpashop.controller;

import com.jpabook.jpashop.domain.dto.request.BookForm;
import com.jpabook.jpashop.domain.entity.item.Book;
import com.jpabook.jpashop.domain.entity.item.Item;
import com.jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(@Validated BookForm bookform, BindingResult result) {
        if (result.hasErrors()) {
            return "items/createItemForm";
        }
        itemService.saveItem(bookform.createBook());
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        model.addAttribute("items", itemService.findItems());
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String update(@PathVariable("itemId") Long id, Model model) {

        Book book = (Book) itemService.findItem(id);
        model.addAttribute("form", new BookForm(book));
        return "items/updateItemForm";
    }
    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@Validated BookForm bookForm, BindingResult result) {
        if (result.hasErrors()) {
            return "items/itemList";
        }
        itemService.updateItem(bookForm.getId(), bookForm.createBook());
        return "redirect:/items";
    }
}
