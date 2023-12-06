package com.jpabook.jpashop.domain.entity;

import com.jpabook.jpashop.enums.DeliveryStatus;
import com.jpabook.jpashop.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Table(name = "ORDERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;
    private LocalDateTime orderDate; // 주문시간
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status; // 주문 상태

    public Order(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.settingOrder(this);
    }

    public Order(Delivery delivery) {
        this.delivery = delivery;
        delivery.settingOrder(this);
    }

    public Order(Member member, Delivery delivery, List<OrderItem> orderItems) {
        this.member = member;
        this.delivery = delivery;
        this.status = OrderStatus.ORDER;
        this.orderDate = LocalDateTime.now();
        orderItems.forEach(it -> addOrderItem(it));
    }

    // 생성 메서드
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        return new Order(member, delivery, Arrays.stream(orderItems).toList());
    }

    /**
     * 주문 취소
     * */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMPLETE) {
            throw new IllegalArgumentException("이미 배송완료 상품");
        }

        this.status = OrderStatus.CANCEL;
        // 주문재고 원복
        orderItems.forEach(OrderItem::cancel);
    }

    /**
     *  주문 총 실제 비용
     * */
    public int getTotalPrice() {
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }
}
