package javaspring.javaspringpractice.repository;

import javaspring.javaspringpractice.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();   //예제용 HashMap 데이터 중복 처리보다는 간단할 구현용
    private static long sequence = 0L;          // 0,1,2 등 key 값 생성 - 예제용.

    @Override
    public Member save(Member member) {
        member.setId(++sequence);           // id 값 하나 올려줌. int auto_increment 랑 역할 같음
        store.put(member.getId(), member);  // store에 member id, member 값 저장
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));          // optional. null이어도 해당 값을 감쌀 수 있음.
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()                      // Lambda식 표현. stream. 값을 반복해서 돌림(내부 반복)
                .filter(member -> member.getName().equals(name))        // member 값이 기존 member랑 일치하면 filtering 되어 걸러짐
                .findAny();                                             // 하나씩 체크 가능. 요소 검색 메소드. 결과가 optional로 null 포함해서 반환됨

    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); //store 객체의 member들 데이터를 모두 가져와 반환됨.
    }

    public void clearStore(){
        store.clear();
    }
}
