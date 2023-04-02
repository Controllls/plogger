package com.baekjo.demo.repository;

import com.baekjo.demo.domain.Membership;
import com.baekjo.demo.domain.MembershipBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MembershipBoardRepository {
    private final EntityManager em;

    public void save(MembershipBoard membershipBoard){
        em.persist(membershipBoard);
    }


    public MembershipBoard findOne(Long id){
        return em.find(MembershipBoard.class, id);
    }


    public List<MembershipBoard> findByMembershipId(Long id){
        return em.createQuery("select m from MembershipBoard m where m.membership.id = :id" , MembershipBoard.class)
                .setParameter("id" , id)
                .getResultList();
    }

    public List<MembershipBoard> findByMembershipName(String name){
        return em.createQuery("select m from MembershipBoard m where m.membership.name = :name" , MembershipBoard.class)
                .setParameter("name" , name)
                .getResultList();
    }
}
