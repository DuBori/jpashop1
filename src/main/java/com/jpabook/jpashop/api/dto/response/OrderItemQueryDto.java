package com.jpabook.jpashop.api.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class OrderItemQueryDto {
    private final Long orderId;
    private final String name;
    private final int orderPrice;
    private final int count;

    public OrderItemQueryDto() {
        orderId = null;
        name = null;
        orderPrice = 0;
        count = 0;
    }
}