package javaspring.javaspringpractice;

import jakarta.persistence.EntityManager;
import javaspring.javaspringpractice.repository.JpaMemberRepository;
import javaspring.javaspringpractice.repository.MemberRepository;
import javaspring.javaspringpractice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

//    기존 Spring, JDBC 코드
//    private DataSource dataSource;
//
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }


    @Bean
    public MemberService memberService(){
        return new MemberService(memoryMemberRepository());
    }

    @Bean
    public MemberRepository memoryMemberRepository(){
//        return new MemoryMemberRepository();                  // # 메모리에 저장하는 코드
//        return new JdbcTemplateMemberRepository(dataSource);  // # JDBC로 DB 접근해서 사용하는 코드
        return new JpaMemberRepository(em);                     // # JPA로 DB 접근해서 사용하는 코드
    }

}
