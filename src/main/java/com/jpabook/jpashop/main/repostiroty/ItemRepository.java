package com.jpabook.jpashop.main.repostiroty;

import com.jpabook.jpashop.main.domain.entity.item.Item;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager entityManager;

    public void save(Item item) {
        if (item.getId() == null) {
            entityManager.persist(item);
        }
        if (item.getId() != null) {
            entityManager.merge(item);
        }
    }

    public Item find(Long id) {
        return entityManager.find(Item.class, id);
    }

    public List<Item> findAll() {
        return entityManager.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
