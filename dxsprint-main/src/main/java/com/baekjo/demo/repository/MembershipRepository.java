package com.baekjo.demo.repository;

import com.baekjo.demo.domain.Member;
import com.baekjo.demo.domain.Membership;
import com.baekjo.demo.domain.MembershipBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MembershipRepository {
    private final EntityManager em;

    public void save(Membership membership) {
        em.persist(membership);
    }

    public List<Membership> findAll() {
        return em.createQuery("select m from Membership m", Membership.class)
                .getResultList();
    }

    public Membership findOne(Long id) {
        return em.find(Membership.class, id);
    }

    public List<Membership> findById(Long id) {
        return em.createQuery("select m from Membership m where m.id = :id", Membership.class)
                .setParameter("id", id)
                .getResultList();
    }

//    public void deleteById(){
//        return em.
//    }

    public List<Membership> findByName(String name) {
        return em.createQuery("select m from Membership m where m.name = :name", Membership.class)
                .setParameter("name", name)
                .getResultList();
    }

}
