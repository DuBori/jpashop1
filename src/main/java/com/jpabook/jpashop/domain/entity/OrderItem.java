package com.jpabook.jpashop.domain.entity;

import com.jpabook.jpashop.domain.entity.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "ORDER_ITEM_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    private int orderPrice; // 주문가격
    private int count; // 주문 수량

    public OrderItem(Item item, int orderPrice, int count) {
        this.item = item;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public void settingOrder(Order order) {
        this.order = order;
    }

    // 생성 매서드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem(item, orderPrice, count);
        item.removeStock(count);
        return orderItem;
    }
    /**
     * 아이템 비즈니스 로직
     * */
    public void cancel() {
        getItem().addStock(count);
    }
    /**
    *  주문 총 가격 조회로직
    * */
    public int getTotalPrice() {
        return orderPrice * count;
    }
}
