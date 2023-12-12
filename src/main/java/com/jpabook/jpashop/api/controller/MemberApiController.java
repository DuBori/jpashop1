package com.jpabook.jpashop.api.controller;

import com.jpabook.jpashop.api.controller.dto.request.CreateMemberRequest;
import com.jpabook.jpashop.api.controller.dto.response.CreateMemberResponse;
import com.jpabook.jpashop.main.domain.entity.Member;
import com.jpabook.jpashop.main.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest member) {
        Long id = memberService.join(member.toEntity());
        return new CreateMemberResponse(id);
    }
}
