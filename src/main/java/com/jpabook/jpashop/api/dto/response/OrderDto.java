package com.jpabook.jpashop.api.dto.response;

import com.jpabook.jpashop.main.domain.entity.Address;
import com.jpabook.jpashop.main.domain.entity.Order;
import com.jpabook.jpashop.main.domain.entity.OrderItem;
import com.jpabook.jpashop.main.enums.OrderStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString

public class OrderDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItems;
    @Getter
    @ToString
    @RequiredArgsConstructor
    private class OrderItemDto {
        private final String name;
        private final int price;
        private final int count;

        public OrderItemDto(OrderItem orderItem) {
            name = orderItem.getItem().getName();
            price = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }
    public OrderDto(Order o) {
        orderId = o.getId();
        name = o.getMember().getName();
        orderDate = o.getOrderDate();
        orderStatus = o.getStatus();
        address = o.getDelivery().getAddress();
        orderItems = o.getOrderItems().stream()
                .map(it -> new OrderItemDto(it))
                .collect(Collectors.toList());
    }


}
