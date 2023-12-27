package javaspring.javaspringpractice.service;

import jakarta.transaction.Transactional;
import javaspring.javaspringpractice.domain.Member;
import javaspring.javaspringpractice.repository.MemberRepository;
import javaspring.javaspringpractice.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// JPA는 DB 변경이 일어날 때 @Transactional이 필요함.
@Transactional          // 추가
public class MemberService {

    private final MemberRepository memberRepository;
    // 외부에서 이 클래스 객체를 사용 가능.
    // 외부에서 memberRepository를 만들어 넣는 것이기 때문에 DI(Dependency Injection)에 해당됨.

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //회원 가입
    public long join(Member member){
        // 같은 이름이 있는 중복 멤버는 허용x
//        Optional<Member> result = memberRepository.findByName(member.getName());  // 기본 소스 코드
//        result.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });

        // 입력된 값 member 객체의 name 값을 꺼내 기존 repository에서 같은 값이 있는지 확인.
        // 위 코드에서 좀 더 간결하게 정리된 코드. ->  Optional<Member> result 생략.
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();

    }

    // 중복되는 회원 이름이 있는지 체크
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }

//  전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

//  회원 1명 조회
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
