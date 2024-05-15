package com.npt.anis.ANIS.member.repository;

import com.npt.anis.ANIS.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.jaas.JaasPasswordCallbackHandler;

public interface MemberRepository extends JpaRepository<Member,String> {
}
