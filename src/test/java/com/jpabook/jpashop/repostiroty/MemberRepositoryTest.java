package com.jpabook.jpashop.repostiroty;

import com.jpabook.jpashop.main.domain.entity.Address;
import com.jpabook.jpashop.main.domain.entity.Member;
import com.jpabook.jpashop.main.service.MemberService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private EntityManager entityManager;
    @Test
    void save() {
        // given
        Member member = new Member("박정현", new Address("서울", "독산", "1동"));

        // when
        Long saveId = memberService.join(member);
        entityManager.flush();
        // then
        assertEquals(member, memberService.findMember(saveId));
    }

    @Test
    public void DuplicateMemberTest() {
        Member member = new Member("박정현", new Address("서울", "독산", "1동"));
        Member member2 = new Member("박정현", new Address("서울", "독산", "1동"));

        assertThrows(IllegalArgumentException.class, () -> {
            memberService.join(member);
            memberService.join(member2);
        });
    }
}