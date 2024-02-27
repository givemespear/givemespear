package com.example.TheEnd.controller;

import com.example.TheEnd.domain.Member;
import com.example.TheEnd.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class HomeController {

    private final MemberService memberService;

    public HomeController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        // 세션에서 userId를 가져와서 name 속성에 저장
        String name = (String) session.getAttribute("userId");
        model.addAttribute("name", name);

        // 이전 로그인 시간 가져오기
        Date lastLoginTime = (Date) session.getAttribute("lastLoginTime");
        model.addAttribute("lastLoginTime", lastLoginTime);

        return "/home";
    }

    @GetMapping("/members/login")
    public String loginPage() {
        return "/members/login";
    }

    @PostMapping("/members/login")
    public String login(@Valid @ModelAttribute MemberForm memberForm, BindingResult bindingResult,
                        HttpServletRequest request, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "/members/login";
        }

        Member member = memberService.login(memberForm);

        if (member == null) {
            bindingResult.rejectValue("name", "loginFail", "로그인 아이디 또는 비밀번호가 틀렸습니다.");
            return "/members/login";
        }

        // 로그인 성공 시 세션에 사용자 ID 저장
        session.setAttribute("userId", member.getName());
        session.setAttribute("id", member.getId()); // 사용자의 ID도 저장할 수 있습니다.
        session.setMaxInactiveInterval(1800); // Session이 30분동안 유지

        // 이전 로그인 시간 저장
        session.setAttribute("lastLoginTime", new Date());

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletRequest request) {
        session.invalidate(); // 세션 무효화
        // 로그아웃 후에는 로그인 페이지로 리다이렉트
        return "redirect:/";
    }

    @GetMapping("/info")
    public String info(HttpSession session, Model model) {
        // 세션에서 userId 가져오기
        String userId = (String) session.getAttribute("userId");
        String id = String.valueOf(session.getAttribute("id"));


        // 세션에 userId가 없는 경우, 로그인 페이지로 리다이렉트
        if (userId == null) {
            return "redirect:/members/login"; // 로그인 페이지로 리다이렉트
        }

        // 세션에 저장된 userId를 모델에 추가하여 info 페이지로 이동
        model.addAttribute("userId", userId);
        model.addAttribute("id", id);
        return "info";
    }

}