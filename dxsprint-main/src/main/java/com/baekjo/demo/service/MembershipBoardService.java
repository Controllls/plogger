package com.baekjo.demo.service;

import com.baekjo.demo.domain.Membership;
import com.baekjo.demo.domain.MembershipBoard;
import com.baekjo.demo.repository.MembershipBoardRepository;
import com.baekjo.demo.repository.MembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MembershipBoardService {
    private final MembershipBoardRepository membershipBoardRepository;

    public Long join(MembershipBoard membershipBoard) {
        membershipBoardRepository.save(membershipBoard);

        return membershipBoard.getId();
    }

    public List<MembershipBoard> findByMembershipId(Long id){
        return membershipBoardRepository.findByMembershipId(id);
    }

    public List<MembershipBoard> findByMembershipName(String name){
        return membershipBoardRepository.findByMembershipName(name);
    }

    public MembershipBoard findOne(Long id) {
        return membershipBoardRepository.findOne(id);
    }

//    public void
}
