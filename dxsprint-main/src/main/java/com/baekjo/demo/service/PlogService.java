package com.baekjo.demo.service;

import com.baekjo.demo.domain.Plog;
import com.baekjo.demo.domain.Plog_Image;
import com.baekjo.demo.repository.PlogImageRepository;
import com.baekjo.demo.repository.PlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PlogService {
    private final PlogRepository plogRepository;
    private final PlogImageRepository plogImageRepository;

    public Long join(Plog plog) {
        plogRepository.save(plog);

        return plog.getId();
    }

    public List<Plog> findByToken(String token) {
        return plogRepository.findByToken(token);

    }

    public List<Plog> findAll(){
        return plogRepository.findAll();
    }


    public List<Plog_Image> findByPath(String path) {
        return plogImageRepository.findByPath(path);

    }

}
