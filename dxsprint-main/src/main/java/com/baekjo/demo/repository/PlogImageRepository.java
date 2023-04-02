package com.baekjo.demo.repository;

import com.baekjo.demo.domain.Plog;
import com.baekjo.demo.domain.Plog_Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PlogImageRepository {
    private final EntityManager em;

    public List<Plog_Image> findByPath(String path){
        return em.createQuery("select m from Plog_Image m where m.image_path = :path" , Plog_Image.class)
                .setParameter("oatg" , path)
                .getResultList();
    }
}
