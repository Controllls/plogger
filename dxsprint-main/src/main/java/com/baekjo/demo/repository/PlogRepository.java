package com.baekjo.demo.repository;

import com.baekjo.demo.domain.Member;
import com.baekjo.demo.domain.Plog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlogRepository {
    private final EntityManager em;

    public void save(Plog plog){
        em.persist(plog);
    }

    public Plog findOne(Long id){
        return em.find(Plog.class, id);
    }

    public List<Plog> findAll() {
        return em.createQuery("select m from Plog m", Plog.class)
                .getResultList();
    }
    public List<Plog> findByToken(String token){
        return em.createQuery("select m from Plog m where m.member.token = :token" , Plog.class)
                .setParameter("token" , token)
                .getResultList();
    }



}