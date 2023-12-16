package com.jpabook.jpashop.api.dto.response;

import com.jpabook.jpashop.main.domain.entity.Address;
import com.jpabook.jpashop.main.domain.entity.Order;
import com.jpabook.jpashop.main.enums.OrderStatus;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter
@ToString

public class SimpleOrderQueryDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public SimpleOrderQueryDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate =  orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}
