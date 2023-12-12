package com.jpabook.jpashop.api.dto.common;

import com.jpabook.jpashop.main.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MemberResponse {
    private int count;
    private List<MemberDto> list;

    public MemberResponse(int size, List<Member> members) {
        this.count = size;
        this.list =  members.stream()
                .map(it -> new MemberDto(it.getId(), it.getName()))
                .collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    private class MemberDto {
        private Long id;
        private String name;
    }


}
