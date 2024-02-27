package com.example.TheEnd.service;

import com.example.TheEnd.controller.MemberForm;
import com.example.TheEnd.domain.Member;
import com.example.TheEnd.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    public Long join(Member member) {
        long start = System.currentTimeMillis();
        try {
            validateDuplicateMember(member); //중복 회원 검증
            memberRepository.save(member);
            return member.getId();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join " + timeMs + "ms");
        }
    }


    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public List<Member> findMembers() {
        long start = System.currentTimeMillis();
        try {
            return memberRepository.findAll();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("findMembers " + timeMs + "ms");
        }
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Member login(MemberForm req) {
        Optional<Member> User = memberRepository.findByName(req.getName());

        // Name과 일치하는 Member가 없으면 null return
        if(User.isEmpty()) {
            return null;
        }

        Member member = User.get();

        // 찾아온 User의 password와 입력된 password가 다르면 null return

        return member;
    }

    public void delete(Long id) {
        // 회원 삭제 기능
        memberRepository.deleteById(id);
    }
}
