package com.jpabook.jpashop.main.domain.dto.request;

import com.jpabook.jpashop.main.domain.entity.Address;
import com.jpabook.jpashop.main.domain.entity.Member;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
    @NotEmpty(message = "이름은 필수값 입니다.")
    private String name;
    private String city;
    private String street;
    private String zipcode;

    public Member createMember() {
        return new Member(name, new Address(city, street, zipcode));
    }
}
