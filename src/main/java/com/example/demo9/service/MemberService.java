package com.example.demo9.service;

import com.example.demo9.entity.Member;
import com.example.demo9.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

  private final MemberRepository memberRepository;

  public Member saveMember(Member member) {
    // 회원 이메일 중복체크 메소드 호출
    validateMemberEmailDuplicationCheck(member.getEmail());

    return memberRepository.save(member);
  }

  private void validateMemberEmailDuplicationCheck(String email) {
    // 회원 이메일 중복체크
    Optional<Member> opMember = memberRepository.findByEmail(email);

    if(opMember.isPresent()) { throw new IllegalStateException("이미 존재하는 회원입니다."); }
  }

  public Optional<Member> getMemberEmailCheck(String email) {
    return memberRepository.findByEmail(email);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<Member> opMember = Optional.ofNullable(memberRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("회원 정보가 없습니다." + email)));

    return User.builder()
            .username(opMember.get().getEmail())
            .password(opMember.get().getPassword())
            .roles(opMember.get().getRole().toString())
            .build();
  }
}
