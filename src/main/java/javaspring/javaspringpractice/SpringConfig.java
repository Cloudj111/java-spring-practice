package javaspring.javaspringpractice;

import javaspring.javaspringpractice.repository.JpaMemberRepository;
import javaspring.javaspringpractice.repository.MemberRepository;
import javaspring.javaspringpractice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    // SpringDataJpaMemberRepository 에서 자동으로 만들어진 구현체가 여기에 주입됨.
    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    // 기존 JPA 코드
//    private EntityManager em;
//
//    @Autowired
//    public SpringConfig(EntityManager em) {
//        this.em = em;
//    }

//    기존 Spring, JDBC 코드
//    private DataSource dataSource;
//
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    // 기존에는 memberService 객체를 반환하는 코드였다면,
    // 변경 후 자동 생성되어 주입된 memberRepository를 사용하게 됨(의존성 주입)
    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }

    // 기존에 사용하던 기본 JPA 저장소 객체를
    // SpringDataJpaMemberRepository에서 자동 생성되어 주입되기 때문에 사용하지 않음.
//    @Bean
//    public MemberRepository memoryMemberRepository(){
//        return new MemoryMemberRepository();                    // # 메모리에 저장하는 코드
//        return new JdbcTemplateMemberRepository(dataSource);    // # JDBC로 DB 접근해서 사용하는 코드
//        return new JpaMemberRepository(em);                     // # JPA로 DB 접근해서 사용하는 코드
//    }

}
