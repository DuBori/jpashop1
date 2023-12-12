package com.jpabook.jpashop.api.controller.dto.request;

import com.jpabook.jpashop.main.domain.entity.Member;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateMemberRequest {
    @NotEmpty
    public String name;
    public Member toEntity() {
        return new Member(name, null);
    }
}
