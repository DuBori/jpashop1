package com.jpabook.jpashop.service;

import com.jpabook.jpashop.main.domain.entity.Address;
import com.jpabook.jpashop.main.domain.entity.Member;
import com.jpabook.jpashop.main.domain.entity.Order;
import com.jpabook.jpashop.main.domain.entity.item.Book;
import com.jpabook.jpashop.main.enums.OrderStatus;
import com.jpabook.jpashop.main.exception.NotEnoughStockException;
import com.jpabook.jpashop.main.repostiroty.OrderRepository;
import com.jpabook.jpashop.main.service.OrderService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@SpringBootTest
@Transactional
class OrderServiceTest {
    
    @Autowired
    private EntityManager em;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void Start_Order_Test() {
        Member member = createMember();

        Book book = createBook("JPA", 3000, 10);

        Long orderId = orderService.orderStart(member.getId(), book.getId(), 3);
        Order one = orderRepository.findOne(orderId);

        assertEquals(orderId, one.getId());
        assertEquals(OrderStatus.ORDER, one.getStatus());
        assertEquals(3 * 3000, one.getTotalPrice());
        assertEquals(7, book.getStockQuantity());
    }
    
    @Test
    public void 상품주문_재고수량초과() throws Exception {
        Member member = createMember();

        Book book = createBook("JPA", 3000, 10);
        assertThrows(NotEnoughStockException.class, () -> {
            Long orderId = orderService.orderStart(member.getId(), book.getId(), 11);
            Order one = orderRepository.findOne(orderId);
        });
    }
    private Member createMember() {
        Member member = new Member("아무게", new Address("독산", "1동", "어딘가"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int quantity) {
        Book book = new Book(null, name, price, quantity, null, null);
        em.persist(book);
        return book;
    }

    @Test
    public void Cancel_Order_Test() {
        Member member = createMember();
        Book book = createBook("JPA", 3000, 10);
        Long orderId = orderService.orderStart(member.getId(), book.getId(), 3);
        orderService.cancelOrder(orderId);
        Order one = orderRepository.findOne(orderId);
        assertEquals(10, book.getStockQuantity());
        assertEquals(OrderStatus.CANCEL, one.getStatus());
    }
}