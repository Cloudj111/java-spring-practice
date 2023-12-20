package javaspring.javaspringpractice.repository;

import javaspring.javaspringpractice.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);         // Java 8이상부터 지원. Null이 발생할 경우를 대비한 optional처리
    Optional<Member> findByName(String name);
    List<Member> findAll();         // findAll(); 지금까지 가져온 모든 회원 리스트를 저장해줌

}
