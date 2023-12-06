package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.entity.item.Book;
import com.jpabook.jpashop.domain.entity.item.Item;
import com.jpabook.jpashop.repostiroty.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public Item updateItem(Long itemId, Book param) {
        Book book = (Book) itemRepository.find(itemId);
        // 해당값 변경후
        book.updateCommonValues(param);
        book.changeAuthorAndIsbn(param);
        return book;
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findItem(Long id) {
        return itemRepository.find(id);
    }

}
