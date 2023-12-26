package javaspring.javaspringpractice;

import javaspring.javaspringpractice.repository.JdbcTemplateMemberRepository;
import javaspring.javaspringpractice.repository.MemberRepository;
import javaspring.javaspringpractice.repository.MemoryMemberRepository;
import javaspring.javaspringpractice.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Bean
    public MemberService memberService(){
        return new MemberService(memoryMemberRepository());
    }

    // 기존 저장소 MemoryMemberRepository에서 JdbcTemplateMemberRepository로 작성 코드를 변경(저장소 위치는 바뀌지 않음)
    @Bean
    public MemberRepository memoryMemberRepository(){
//        return new MemoryMemberRepository();
        return new JdbcTemplateMemberRepository(dataSource);
    }

}
