package com.baekjo.demo.repository;

import com.baekjo.demo.domain.Member;
import com.baekjo.demo.domain.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

//    public void save(Member member){
//        em.persist(member);
//    }

    public void save(Member member){
        if(member.getId() == null){
            em.persist(member);
        }else{
            em.merge(member);
        }
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findByEmail(String email){
        return em.createQuery("select m from Member m where m.email = :email" , Member.class)
                .setParameter("email" , email)
                .getResultList();
    }

    public List<Member> findByToken(String token){
        return em.createQuery("select m from Member m where m.token = :token" , Member.class)
                .setParameter("token" , token)
                .getResultList();
    }


    public List<String> findRunning(MemberStatus status){
        return em.createQuery("select token from Member m where m.status = :status", String.class)
                .setParameter("status" , status)
                .getResultList();
    }

    public List<String> findRank() {
        return em.createQuery("select token from Member m order by m.num desc" , String.class)
                .getResultList();
    }
    public List<String> findRunningMembership(Long id , MemberStatus status) {
        return em.createQuery("select token from Member m where membershipId = :id and m.status = :status" , String.class)
                .setParameter("id" , id)
                .setParameter("status" , status)
                .getResultList();
    }
    public List<String> findRankMembership(Long id) {
        return em.createQuery("select token from Member m where membershipId = :id order by m.num desc" , String.class)
                .setParameter("id" , id)
                .getResultList();
    }

//    public void findByCount(Long aLong) {
//        return em.createQuery("select m from Member m where m.")
//    }
}