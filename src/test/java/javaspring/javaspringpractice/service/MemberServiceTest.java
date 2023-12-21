package javaspring.javaspringpractice.service;

import javaspring.javaspringpractice.domain.Member;
import javaspring.javaspringpractice.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    // 각 메소드가 실행되기 전에 실행.
    @BeforeEach
    public void beforeEach(){
         memberRepository = new MemoryMemberRepository();
         memberService = new MemberService(memberRepository);
    }

    // 데이터 초기화. 각 메소드 테스트 시 입력된 데이터가 없는 상태로 테스트 하기 위함.
    @AfterEach
    public void clearData(){
        memberRepository.clearStore();
    }

    /*
    * given - when - then 문법

        given - 주어진 상황. 주어진 값

        회원 가입 시 Member 객체에 입력 값이 들어옴

        when - 어떤 변수에 값을 넣거나, 메소드가 동작하는 등 작업이 진행되는 상태

        Member 객체 안의 값(id, name)을 MemberService.join 메소드를 통해 저장소에 저장.

        then - when 이후에 확인하거나 이어지는 작업을 작성.

        저장소에 값을 저장할 때 중복되는 name이 있는지 확인
    * */

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("John");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }
    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");


        //when
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        /*
        memberService.join(member1);
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/

        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}