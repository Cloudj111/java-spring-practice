package javaspring.javaspringpractice.repository;

import javaspring.javaspringpractice.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


// test를 먼저 만드는 방식도 있음. (tdd) 테스트 주도 개발 방식.
// package 단위 테스트 가능

class MemoryMemberRepositoryTest {          // test용이라 public 생략 가능
    MemoryMemberRepository repository = new MemoryMemberRepository();

    // AfterEach. 어떤 메소드 하나가 동작이 끝날 때 실행이 됨. 콜백. store 데이터 지움.
    // 각 테스트를 할 때마다 공용 데이터를 삭제하여 다른 메소드 영향 없이 테스트 진행.
    @AfterEach
    public void afterEach(){
        repository.clearStore();    // store 데이터 지움.
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);        //repository에 member 데이터 저장

        Member result = repository.findById(member.getId()).get();  //optioanl에 저장한 값은 get()으로 꺼낼 수 있음
        //Assertions.assertEquals(member, result);   // 위의 Member member와 찾은 member 값이 일치하는지 체크
        assertThat(member).isEqualTo(result); // 다른 방식 Assertions.assertThat
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);


        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get(); //spring1이란 member 값 찾기

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);


        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll(); //repository의 모든 데이터를 가져와 list result에 넣음

        assertThat(result.size()).isEqualTo(2); //result 객체의 사이즈가 2인지 체크
    }


}
