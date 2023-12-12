package com.jpabook.jpashop.main.controller;

import com.jpabook.jpashop.main.domain.dto.request.MemberForm;
import com.jpabook.jpashop.main.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members")
    public String list(Model model) {
        model.addAttribute("members", memberService.findMembers());
        return "members/memberList";
    }
    @GetMapping("/members/new")
    public String createForm(final Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }
    @PostMapping("/members/new")
    public String createFormPost(@Validated MemberForm memberForm, BindingResult result) {
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }
        memberService.join(memberForm.createMember());
        return "redirect:/";
    }

}
