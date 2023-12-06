package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.dto.request.OrderSearch;
import com.jpabook.jpashop.domain.entity.Delivery;
import com.jpabook.jpashop.domain.entity.Member;
import com.jpabook.jpashop.domain.entity.Order;
import com.jpabook.jpashop.domain.entity.OrderItem;
import com.jpabook.jpashop.domain.entity.item.Item;
import com.jpabook.jpashop.repostiroty.ItemRepository;
import com.jpabook.jpashop.repostiroty.MemberRepository;
import com.jpabook.jpashop.repostiroty.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final MemberRepository memberRepository;

    private final ItemRepository itemRepository;

    /**
     * 주문
     **/
    @Transactional
    public Long orderStart(Long memberId, Long itemId, int count) {
        // 엔티티 조회
        Member member = memberRepository.find(memberId);
        Item item = itemRepository.find(itemId);

        Delivery delivery = new Delivery(member.getAddress());

        //주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     * */
    @Transactional
    public void cancelOrder(Long orderId) {
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        order.cancel();
    }

    /**
     * 주문 검색
     * */
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByCriteria(orderSearch);
    }

}
