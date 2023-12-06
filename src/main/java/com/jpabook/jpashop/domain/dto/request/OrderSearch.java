package com.jpabook.jpashop.domain.dto.request;

import com.jpabook.jpashop.enums.OrderStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;

}
