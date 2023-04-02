package com.baekjo.demo.repository;

import com.baekjo.demo.domain.CertificationBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CertificationBoardRepository {
    private final EntityManager em;

    public void save(CertificationBoard certificationBoard) {
        em.persist(certificationBoard);
    }

    public CertificationBoard findOne(Long id) {
        return em.find(CertificationBoard.class, id);
    }


    public List<CertificationBoard> findByMembershipId(Long id) {
        return em.createQuery("select m from CertificationBoard m where m.membership.id = :id", CertificationBoard.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<CertificationBoard> findByMembershipName(String name) {
        return em.createQuery("select m from CertificationBoard m where m.membership.name = :name", CertificationBoard.class)
                .setParameter("name", name)
                .getResultList();
    }
}
