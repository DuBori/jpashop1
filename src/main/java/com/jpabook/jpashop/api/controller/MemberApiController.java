package com.jpabook.jpashop.api.controller;

import com.jpabook.jpashop.api.dto.request.CreateMemberRequest;
import com.jpabook.jpashop.api.dto.request.UpdateMemberRequest;
import com.jpabook.jpashop.api.dto.response.CreateMemberResponse;
import com.jpabook.jpashop.api.dto.common.MemberResponse;
import com.jpabook.jpashop.api.dto.response.ResponseEntity;
import com.jpabook.jpashop.api.dto.response.UpdateMemberResponse;
import com.jpabook.jpashop.main.domain.entity.Member;
import com.jpabook.jpashop.main.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> getListV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public ResponseEntity getListV2() {
        List<Member> members = memberService.findMembers();
        return new ResponseEntity(new MemberResponse(members.size(), members));
    }

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

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMember(@PathVariable("id") Long id
            , @RequestBody @Valid UpdateMemberRequest updateMember) {
        Long updateId = memberService.update(id, updateMember.getName());
        Member member = memberService.findMember(updateId);
        return new UpdateMemberResponse(member.getId(), member.getName());
    }

}
