package com.example.nooffsetpaging.domain.member.model.repository;

import com.example.nooffsetpaging.domain.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
