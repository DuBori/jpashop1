package com.jpabook.jpashop.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderForm {
    private Long memberId;
    private Long itemId;

    private int count;
}
