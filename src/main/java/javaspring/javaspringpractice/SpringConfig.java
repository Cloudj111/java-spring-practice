package javaspring.javaspringpractice;

import javaspring.javaspringpractice.repository.MemoryMemberRepository;
import javaspring.javaspringpractice.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService(){
        return new MemberService(memoryMemberRepository());
    }

    @Bean
    public MemoryMemberRepository memoryMemberRepository(){
        return new MemoryMemberRepository();
    }
}
