package com.example.TheEnd.controller;

import com.example.TheEnd.domain.Member;
import com.example.TheEnd.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/members")
@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = "/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping(value = "/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping(value = "")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @GetMapping(value = "/memberList")
    public String deleteForm(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/deleteMemberForm";
    }


    @DeleteMapping("/{id}")
    public String deleteMember(@PathVariable(value = "id") Long id) {
        memberService.delete(id);
        return "redirect:/members"; // 삭제 후 회원 목록 페이지로 리다이렉트합니다.
    }
}