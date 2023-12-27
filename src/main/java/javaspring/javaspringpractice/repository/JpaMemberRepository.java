package javaspring.javaspringpractice.repository;

import jakarta.persistence.EntityManager;
import javaspring.javaspringpractice.domain.Member;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    // JPA는 모든 것이 EntityManager로 동작함
    // build.gradle로 jpa 라이브러리를 받으면 이 개체를 Spring이 자동으로 생성하여 주입함.
    // application.properties, library 등 관련 설정이 다 적용되어 EntiryNanager 객체가 생성됨.
    // 내부에 DataSource 객체를 갖고 있음.
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }
    
    // member 객체에 id, name 등을 자동으로 넣어 DB에 저장함
    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    // em.find(조회할 식별자, 객체) 주어진 값을 조회하여 반환하는 메소드.
    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    // createQuery로 query문을 만들어 질의.
    // select m from Member m = select * from Member as m
    // where m.name = :name == where m.name = name
    // Member.class로 조회
    // setParameter("name", name) select문의 :name -> 컬럼명, 컬럼에 대응하는 문자열 값 저장
    // getResultList() 결과 값을 List로 반환 받음.
    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }



    // JPQL
    // 객체, Entity를 대상으로 쿼리를 날리는 문법 -> SQL로 번역되어 DB에 쿼리 실행
    // 객체 자체를 조회하는 메소드.
    @Override
    public List<Member> findAll() {
//        # JPQL 쿼리문 1
//        List<Member> result = em.createQuery("select m from Member (as) m", Member.class)
//                .getResultList();
//        return result;
//        # JPQL 쿼리문 2 // Refactor this : 단축키 ctrl+alt+shift+t - inline variable (window)
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
