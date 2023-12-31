package javaspring.javaspringpractice.controller;

import javaspring.javaspringpractice.domain.Member;
import javaspring.javaspringpractice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();               //멤버 객체 생성
        member.setName(form.getName());             //받은 name input 값을 member 객체에 저장

        memberService.join(member);                 // memberService 클래스의 join 메서드로 member 객체 처리. 저장소에 회원 등록

        return "redirect:/";                        // 작업을 마무리한 뒤 홈으로 다시 보냄(redirect:/)
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();   // List Member로 제네릭(<>) 타입 설정 = 모든 member 정보 가져와서 members 변수에 저장
        model.addAttribute("members", members);   // model에 key=members / value=members(List) 저장
        return "members/memberList";                          // members/memberList 를 반환. 그 위치로 이동
    }
}

