package com.baekjo.demo.service;

import com.baekjo.demo.domain.Member;
import com.baekjo.demo.domain.MemberStatus;
import com.baekjo.demo.repository.MemberRepository;
import com.baekjo.demo.repository.PlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PlogRepository plogRepository;
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);

        return member.getId();
    }

    public Long update(Member member) {
        memberRepository.save(member);

        return member.getId();
    }


    public void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByEmail(member.getEmail());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재 하는 회원 입니다");
        }
    }

    public List<String> findRunning(MemberStatus status){
        return memberRepository.findRunning(status);
    }

    public List<String> findMembershipRunning(Long id ,MemberStatus status){
        return memberRepository.findRunningMembership(id ,status);
    }

    public Member findOne(Long id) {return memberRepository.findOne(id);}

    public List<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);

    }

    public List<Member> findByToken(String token) {
        return memberRepository.findByToken(token);

    }

    public List<String> findRank() {return memberRepository.findRank();}

    public List<String> findRankMembership(Long id) {return memberRepository.findRankMembership(id);}

//    public List<Plog> findByDate(String token, String year ,String month ,String day ,String hour){
//        return plogRepository.findByToken(token);
//    }

//    public void findByCount(Long aLong) {
//        return plogRepository.findByCount(aLong);
//
//    }
}