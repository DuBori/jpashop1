package com.jpabook.jpashop.api.dto.response;

import com.jpabook.jpashop.main.domain.entity.Address;
import com.jpabook.jpashop.main.enums.OrderStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@RequiredArgsConstructor
public class OrderFlatDto {
    private final Long orderId;
    private final String name;
    private final LocalDateTime orderDate;
    private final OrderStatus orderStatus;
    private final Address address;
    private final String ItemName;
    private final int orderPrice;
    private final int count;
}
