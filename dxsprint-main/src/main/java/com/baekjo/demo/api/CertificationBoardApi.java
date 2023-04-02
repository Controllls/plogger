package com.baekjo.demo.api;

import com.baekjo.demo.domain.*;
import com.baekjo.demo.exception.NotFoundImageException;
import com.baekjo.demo.service.CertificationBoardService;
import com.baekjo.demo.service.MemberService;
import com.baekjo.demo.service.MembershipService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CertificationBoardApi {
    private final MembershipService membershipService;
    private final CertificationBoardService certificationBoardService;
    private final MemberService memberService;

    @PostMapping("/save/certificationBoard")
    public String saveCertificationBoard(CertificationRequest request) {
        CertificationBoard certificationBoard = new CertificationBoard();

        List<Member> members = memberService.findByToken(request.getToken());
        Member member = null;
        if (!members.isEmpty()) {
            member = members.get(0);
        }else{
            return "멤버없음";
        }

        member.setNum(member.getNum()+1);

        certificationBoard.setMember(member);
        certificationBoard.setLocalDateTime(LocalDateTime.now());


//        membershipBoard.setWriter(request.getWriter());

        if (member.getMembershipId() != 0) {
            certificationBoard.setMembership(membershipService.findOne(member.getMembershipId()));
        } else {
            return "멤버쉽 없음";
        }
        certificationBoard.setContent(member.getNickname() + "님이 플로깅인증 하셨습니다.");

        CertificationBoard_Image certificationBoard_image = new CertificationBoard_Image();
        certificationBoard_image.setFile_name(request.getImage().getOriginalFilename());

        String uuid = UUID.randomUUID().toString().replace("-", "");
        String path = "/home/ec2-user/images/";
//        String path = "/Users/choejong-geun/Desktop/images/"+request.getToken()+"/";
        Path imagePath = Paths.get(path + uuid + request.getImage().getOriginalFilename());
        //사진 서버에 저장
        try {
            Files.write(imagePath, request.getImage().getBytes());
        } catch (Exception e) {
            log.info("사진 저장 실패");
            return "실패";
        }


        certificationBoard_image.setImagePath(path + uuid+ request.getImage().getOriginalFilename());
        certificationBoard.setCertificationBoard_image(certificationBoard_image);

        certificationBoardService.join(certificationBoard);

        return "성공";

    }

    @Data
    @AllArgsConstructor
    static class CertificationRequest {
        private MultipartFile image;
        private String token;
//        private String writer;
    }

    @GetMapping("/certificationBoardDataName/{membershipName}")
    public List<CertificationBoardResponse> getMembershipBoardData(@PathVariable("membershipName") String name) {


        List<CertificationBoard> certificationBoards = certificationBoardService.findByMembershipName(name);
//        CertificationBoard certificationBoard = certificationBoards.get(0);




//        List<CertificationBoard> certificationBoards = certificationBoardService.findByMembershipId(id);

        return certificationBoards.stream()
                .map(CertificationBoardResponse::new)
                .collect(Collectors.toList());

    }

    @Data
    @AllArgsConstructor
    static class CertificationBoardResponse {
        private String token;
        private Long ImageId;

        private int month;
        private int day;

        public CertificationBoardResponse(CertificationBoard m) {
            this.ImageId = m.getCertificationBoard_image().getId();
            this.month = m.getLocalDateTime().getMonthValue();
            this.day = m.getLocalDateTime().getDayOfMonth();
            this.token = m.getMember().getToken();
        }
    }

    @GetMapping("/certificationBoardImage/{id}")
    public ResponseEntity<Resource> getCertBoardImageId(@PathVariable("id") Long id) {

        CertificationBoard certificationBoardServiceOne = certificationBoardService.findOne(id);
        try {
            FileSystemResource resource = new FileSystemResource(certificationBoardServiceOne.getCertificationBoard_image().getImagePath());
            if (!resource.exists()) {
                throw new NotFoundImageException();
            }
            HttpHeaders header = new HttpHeaders();
            Path filePath = null;
            filePath = Paths.get(certificationBoardServiceOne.getCertificationBoard_image().getImagePath());
            header.add("Content-Type", Files.probeContentType(filePath));

            return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
        } catch (Exception e) {
            throw new NotFoundImageException();
        }
    }

}
