package com.example.TheEnd.repository;

import com.example.TheEnd.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>{
    Optional<Member> findByName(String name);

}