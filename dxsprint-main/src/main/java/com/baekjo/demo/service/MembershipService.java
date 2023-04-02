package com.baekjo.demo.service;


import com.baekjo.demo.domain.Member;
import com.baekjo.demo.domain.Membership;
import com.baekjo.demo.repository.MembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MembershipService {
    private final MembershipRepository membershipRepository;

    public Long join(Membership membership) {
        validateDuplicateMemberShip(membership);
        membershipRepository.save(membership);


        return membership.getId();
    }

    public void validateDuplicateMemberShip(Membership membership) {
        List<Membership> findMemberships = membershipRepository.findByName(membership.getName());
        if (!findMemberships.isEmpty()) {
            throw new IllegalStateException("이미 존재 하는 멤버십 입니다");
        }
    }

    public Membership findOne(Long id){
        return membershipRepository.findOne(id);


    }

    public List<Membership> findAll(){
        return membershipRepository.findAll();
    }

    public List<Membership> findbyName(String name){
        return membershipRepository.findByName(name);

    }


    public List<Membership> findById(Long id){
        return membershipRepository.findById(id);

    }

}
