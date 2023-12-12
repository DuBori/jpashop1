package com.jpabook.jpashop.main.service;

import com.jpabook.jpashop.main.domain.entity.Member;
import com.jpabook.jpashop.main.repostiroty.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    
    /**
     * 회원가입
     */
    @Transactional
    public Long join(final Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    /**
     *
     */
    private void validateDuplicateMember(final Member member) {
        List<Member> list = memberRepository.findByName(member.getName());
        if (!list.isEmpty()) {
            throw new IllegalArgumentException("존재하는 계정입니다.");
        }
    }

    // 회원 전체 조회
    @Transactional(readOnly = true)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 단건 조회
    @Transactional(readOnly = true)
    public Member findMember(final Long id) {
        return memberRepository.find(id);
    }

}
