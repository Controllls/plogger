package com.baekjo.demo.api;


import com.baekjo.demo.domain.*;
import com.baekjo.demo.exception.NotFoundImageException;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberApi {

    private final MemberService memberService;
    private final MembershipService membershipService;
    public String encodeBcrypt(String planeText, int strength) {
        return new BCryptPasswordEncoder(strength).encode(planeText);
    }

    @PostMapping("/save/members")
    public CreateMemberResponse saveMember(CreateMemberRequest request) {
        String uuid = UUID.randomUUID().toString().replace("-", "");

        String path = "/home/ec2-user/" + uuid + "/";
//        String path = "/Users/choejong-geun/Desktop/images/" + uuid + "/";
        File Folder = new File(path);
        if (!Folder.exists()) {
            try{
                Folder.mkdir(); //폴더 생성합니다.
            }
            catch(Exception e){
                log.info("회원 폴더 생성 실패");
                e.getStackTrace();
            }
        }

        Member member = new Member();
        member.setName(request.getName());
        member.setToken(uuid);
        member.setNickname(request.getNickname());
        member.setEmail(request.getEmail());
        member.setPassword(encodeBcrypt(request.getPassword() , 10));
        member.setNum(0L);
        member.setStatus(MemberStatus.STAY);
        member.setMembershipId(0L);

        Path imagePath = Paths.get(path + request.getImage().getOriginalFilename());
        //사진 서버에 저장
        try {
            Files.write(imagePath, request.getImage().getBytes());
        } catch (Exception e) {
            log.info("사진 저장 실패");
        }
        Member_Image member_image = new Member_Image();
        member_image.setImagePath(path + request.getImage().getOriginalFilename());
        member_image.setFile_name(request.getImage().getOriginalFilename());
        member.setMember_image(member_image);


        if (request.getTall() != null) {
            member.setTall(request.getTall());
        }else{
            member.setTall(0F);
        }
        if (request.getWeight() != null) {
            member.setWeight(request.getWeight());
        }else{
            member.setWeight(0F);
        }
        if (request.getAge() != null) {
            member.setAge(request.getAge());
        }else{
            member.setAge(0L);
        }


        Long id = memberService.join(member);
        return new CreateMemberResponse(uuid);


    }



    @Data
    @AllArgsConstructor
    static class CreateMemberRequest {
        private String email;
        private String token;
        private String name;
        private String nickname;
        private String password;

        private Long age;
        private Float tall;
        private Float weight;

        private MultipartFile image;
    }

    @Data
    @AllArgsConstructor
    static class CreateMemberResponse {

        private String token;

    }

    //todo member age tall weight password page
    @PostMapping("/member/updateProfile")
    private String MemberUpdateProfile(MemberUpdateResquest resquest){
        List<Member> members = memberService.findByToken(resquest.getToken());
        Member member = members.get(0);

        member.setAge(resquest.getAge());
        member.setTall(resquest.getTall());
        member.setWeight(resquest.getWeight());
//        member.setPassword(resquest.getPassword()); //이거 이상
        memberService.update(member);

        return "성공";
    }



    @Data
    @AllArgsConstructor
    static class MemberUpdateResquest{
        private String token;
        private Long age;
        private Float weight;
        private Float tall;
    }

    //todo membership join
    @GetMapping("/member/membershipjoin/{token}/{name}")
    private String MembershipJoin(@PathVariable("token") String token,
                                @PathVariable("name") String name) {
        List<Member> members = memberService.findByToken(token);
        Member member = null;
        if (!members.isEmpty()){
            member = members.get(0);
        }else{
            return "멤버없음";
        }

        List<Membership> memberships = membershipService.findbyName(name);
        Membership membership = null;
        if (!memberships.isEmpty()) {
            membership = memberships.get(0);
        }else{
            return "멤버쉽 없음";
        }


        member.setMembershipId(membership.getId());

        memberService.update(member);


        return "가입완료";
    }

    @GetMapping("/member/membershipseparate/{token}")
    private String MembershipSeparate(@PathVariable("token") String token) {
        List<Member> members = memberService.findByToken(token);
        Member member = null;
        if (!members.isEmpty()){
            member = members.get(0);
        }else{
            return "멤버없음";
        }

        member.setMembershipId(0L);

        memberService.update(member);


        return "탈퇴완료";
    }


    //todo get member membershipId
    @GetMapping("/membershipId/{userToken}")
    public Long getMembershipId(@PathVariable("userToken") String userToken){
        List<Member> findmembers = memberService.findByToken(userToken);
        Member member;
        if (!findmembers.isEmpty()){
            member = findmembers.get(0);
        }else{
            return null;
        }

        return member.getMembershipId();
    }

    @GetMapping("/membershipName/{userToken}")
    public String getMembershipName(@PathVariable("userToken") String userToken){
        List<Member> findmembers = memberService.findByToken(userToken);
        Member member;
        if (!findmembers.isEmpty()){
            member = findmembers.get(0);
        }else{
            return "멤버 없음";
        }

        if (member.getMembershipId() != 0) {
            Membership one = membershipService.findOne(member.getMembershipId());
            return one.getName();
        }else{
            return "멤버쉽 없음";
        }

    }

    @GetMapping("/memberRunning/{token}")
    public void beRun(@PathVariable("token") String token){
        List<Member> members = memberService.findByToken(token);
        Member member = members.get(0);
        member.setStatus(MemberStatus.RUN);

        memberService.update(member);
    }

    @GetMapping("/memberRunningStay/{token}")
    public void beStay(@PathVariable("token") String token){
        List<Member> members = memberService.findByToken(token);
        Member member = members.get(0);
        member.setStatus(MemberStatus.STAY);

        memberService.update(member);
    }

    @GetMapping("/memberNickName/{token}")
    public String getNickName(@PathVariable("token") String token){
        List<Member> members = memberService.findByToken(token);
        Member member = members.get(0);
        return member.getNickname();
    }


    @GetMapping("/body/{token}")
    public List<String> getBody(@PathVariable("token") String token){
        List<Member> members = memberService.findByToken(token);
        Member member = members.get(0);
        List<String> info = new ArrayList<>();
        info.add(String.valueOf(member.getTall()));
        info.add(String.valueOf(member.getAge()));
        info.add(String.valueOf(member.getWeight()));

        return info;
    }

    @GetMapping("/userInfo/{token}")
    public List<String> getInfo(@PathVariable("token") String token){
        List<Member> members = memberService.findByToken(token);
        Member member = members.get(0);


        List<String> list = new ArrayList<>();
        list.add("나이");
        list.add(member.getAge().toString());
        list.add("키");
        list.add(member.getTall().toString());
        list.add("몸무게");
        list.add(member.getWeight().toString());


        return list;
    }


//    //todo 맴버 프로필 사진
    @GetMapping("/memberProfileImage/{userToken}")
    public ResponseEntity<Resource> getImage(@PathVariable("userToken") String userToken){
        List<Member> findmembers = memberService.findByToken(userToken);
        Member member = findmembers.get(0);

//        Member_Image member_image = member.getMember_images().get(0);

        try {
            FileSystemResource resource = new FileSystemResource(member.getMember_image().getImagePath());
            if (!resource.exists()) {
                throw new NotFoundImageException();
            }
            HttpHeaders header = new HttpHeaders();
            Path filePath = null;
            filePath = Paths.get(member.getMember_image().getImagePath());
            header.add("Content-Type", Files.probeContentType(filePath));

            return  new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
        } catch (Exception e) {
            throw new NotFoundImageException();
        }
    }


    //todo profile update
    @PostMapping("/update/userProfileImage")
    public String updateMemberProfile(MultipartFile image , String token){
        List<Member> byToken = memberService.findByToken(token);
        Member member = byToken.get(0);



        Path imagePath = Paths.get(member.getMember_image().getImagePath());
        //사진 서버에 저장
        try {
            Files.write(imagePath, image.getBytes());
        } catch (Exception e) {
            log.info("사진 변경 실패");
            return "실패";
        }

//
//        Member_Image member_image = new Member_Image();
//        member_image.setImagePath(path + request.getImage().getOriginalFilename());
//        member_image.setFile_name(request.getImage().getOriginalFilename());
//        member.setMember_image(member_image);


        return "성공";
    }
}
