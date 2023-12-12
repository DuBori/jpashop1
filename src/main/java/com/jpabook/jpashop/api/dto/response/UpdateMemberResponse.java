package com.jpabook.jpashop.api.dto.response;

import com.jpabook.jpashop.main.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateMemberResponse {
    private Long id;
    private String name;
}
