package com.jpabook.jpashop;

import com.jpabook.jpashop.main.domain.entity.*;
import com.jpabook.jpashop.main.domain.entity.item.Book;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
/**
 *  총 주문 2개
 * */
@Component
@RequiredArgsConstructor
public class initDB {
    private final InitService initService;
    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            Member mem1= getMember("mem1", new Address("서울", "1", "1111"));
            em.persist(mem1);

            Book book1 = new Book(null, "JPA1", 10000, 100, null, null);
            Book book2 = new Book(null, "JPA2", 20000, 100, null, null);
            em.persist(book1);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Order order = Order.createOrder(mem1, new Delivery(mem1.getAddress()), orderItem1, orderItem2);

            em.persist(order);
        }

        public void dbInit2() {
            Member mem1= getMember("mem2", new Address("서울", "1", "1111"));
            em.persist(mem1);

            Book book1 = new Book(null, "JPA1", 10000, 100, null, null);
            Book book2 = new Book(null, "JPA2", 20000, 100, null, null);
            em.persist(book1);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Order order = Order.createOrder(mem1, new Delivery(mem1.getAddress()), orderItem1, orderItem2);

            em.persist(order);
        }

        private Member getMember(String name, Address address) {
            return new Member(name, address);
        }
    }
}

