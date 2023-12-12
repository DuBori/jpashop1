package com.jpabook.jpashop.main.domain.dto.request;

import com.jpabook.jpashop.main.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;

}
