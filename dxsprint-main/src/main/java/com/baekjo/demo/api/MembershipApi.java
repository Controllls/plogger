package com.baekjo.demo.api;


import com.baekjo.demo.domain.*;
import com.baekjo.demo.exception.NotFoundImageException;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MembershipApi {
    private final MembershipService membershipService;

    @PostMapping("/save/membership")
    public String saveMembership(MembershipRequest request) {
        Membership membership = new Membership();
        membership.setName(request.getName());
        membership.setContent(request.getContent());
        membership.setLeader(request.getLeader());
        membership.setTip(request.getTip());
        membership.setRule(request.getRule());
        membership.setNumber(request.getNumber());


        Membership_Image membership_image = new Membership_Image();

        String path = "/home/ec2-user/images/";
//        String path = "/Users/choejong-geun/Desktop/images/";

        Path imagePath = Paths.get(path + request.getImage().getOriginalFilename());
        //사진 서버에 저장
        try {
            Files.write(imagePath, request.getImage().getBytes());
        } catch (Exception e) {
            log.info("사진 저장 실패");
            return "실패";
        }

        membership_image.setImagePath(path + request.getImage().getOriginalFilename());
        membership_image.setFile_name(request.getImage().getOriginalFilename());
        membership.setMembership_image(membership_image);

        membershipService.join(membership);
        return "성공";
    }


    @Data
    @AllArgsConstructor
    static class MembershipRequest {
        private MultipartFile image;
        private String name;
        private String content;
        private String leader;
        private Long number;
        private String rule;
        private String tip;

    }

    @GetMapping("/membership/{name}")
    private MembershipResponse getMembership(@PathVariable("name") String name) {
        List<Membership> memberships = membershipService.findbyName(name);
        Membership membership;
        if (!memberships.isEmpty()) {
            membership = memberships.get(0);
        } else {
            return null;
        }
        MembershipResponse membershipResponse = new MembershipResponse();

        membershipResponse.setName(membership.getName());
        membershipResponse.setId(membership.getId());
        membershipResponse.setNumber(membership.getNumber());
        membershipResponse.setLeader(membership.getLeader());
        membershipResponse.setContent(membership.getContent());
        membershipResponse.setRule(membership.getRule());
        membershipResponse.setTip(membership.getTip());

        return membershipResponse;
    }

    @GetMapping("/membership/image/{name}")
    private ResponseEntity<Resource> getMembershipImage(@PathVariable("name") String name) {
        List<Membership> findMemberships = membershipService.findbyName(name);
        Membership membership;
        if (!findMemberships.isEmpty()) {
            membership = findMemberships.get(0);
        } else {
            return null;
        }

        Membership_Image membership_image = membership.getMembership_image();

        try {
            FileSystemResource resource = new FileSystemResource(membership_image.getImagePath());
            if (!resource.exists()) {
                throw new NotFoundImageException();
            }
            HttpHeaders header = new HttpHeaders();
            Path filePath = null;
            filePath = Paths.get(membership_image.getImagePath());
            header.add("Content-Type", Files.probeContentType(filePath));

            return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
        } catch (Exception e) {
            throw new NotFoundImageException();
        }
    }

    @Data
    static class MembershipResponse {
        private Long id;
        private String name;
        private String content;
        private String leader;
        private Long number;
        private String rule;
        private String tip;

    }


    @GetMapping("/membershipNameList")
    public List<String> getMembershipNameList() {
        List<Membership> all = membershipService.findAll();

        List<String> list = new ArrayList<>();
        for (int i = 0; i < all.size(); i++) {
            list.add(all.get(i).getName());
        }


        return list;
    }

//    @DeleteMapping
}
