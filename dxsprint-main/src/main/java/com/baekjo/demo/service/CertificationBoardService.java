package com.baekjo.demo.service;

import com.baekjo.demo.domain.CertificationBoard;
import com.baekjo.demo.repository.CertificationBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CertificationBoardService {
    private final CertificationBoardRepository certificationBoardRepository;

    public Long join(CertificationBoard certificationBoard) {
        certificationBoardRepository.save(certificationBoard);

        return certificationBoard.getId();
    }

    public List<CertificationBoard> findByMembershipId(Long id){
        return certificationBoardRepository.findByMembershipId(id);
    }

    public List<CertificationBoard> findByMembershipName(String name){
        return certificationBoardRepository.findByMembershipName(name);
    }

    public CertificationBoard findOne(Long id) {
        return certificationBoardRepository.findOne(id);
    }

}
