package javaspring.javaspringpractice.service;

import jakarta.transaction.Transactional;
import javaspring.javaspringpractice.domain.Member;
import javaspring.javaspringpractice.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {


    @Autowired MemberRepository memberRepository;
    @Autowired MemberService memberService;

    @Test
    public void join() {
        // given
        Member member = new Member();
        member.setName("Hello");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberRepository.findById(saveId).get();
        assertEquals(member.getName(), findMember.getName());
    }

    @Test
    public void validDuplicatedMember() throws Exception{
        // given
        Member member1 = new Member();
        member1.setName("Sprint");

        Member member2 = new Member();
        member2.setName("Sprint");

        // when
        memberService.join(member1);
        IllegalStateException e  = assertThrows(IllegalStateException.class,
            () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }
}