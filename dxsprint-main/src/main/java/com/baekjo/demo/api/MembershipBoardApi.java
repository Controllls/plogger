package com.baekjo.demo.api;

import com.baekjo.demo.domain.*;
import com.baekjo.demo.exception.NotFoundImageException;
import com.baekjo.demo.service.MemberService;
import com.baekjo.demo.service.MembershipBoardService;
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
public class MembershipBoardApi {
    private final MembershipService membershipService;
    private final MemberService memberService;

    private final MembershipBoardService membershipBoardService;

    @PostMapping("/save/membershipBoard")
    public String saveMembershipBoard(MembershipBoardRequest request){
        MembershipBoard membershipBoard = new MembershipBoard();

        List<Member> members = memberService.findByToken(request.getToken());
        Member member = null;
        if (!members.isEmpty()){
            member = members.get(0);
        }
        //todo writer
        membershipBoard.setMember(member);
        membershipBoard.setLocalDateTime(LocalDateTime.now());
        membershipBoard.setTitle(request.getTitle());

        if (member.getMembershipId() != 0) {
            membershipBoard.setMembership(membershipService.findById(member.getMembershipId()).get(0));
//            membershipBoard.setMembership(membershipService.findOne(member.getMembershipId()));
        }else{
            return "멤버쉽 없음";
        }

        MembershipBoard_Image membershipBoard_image = new MembershipBoard_Image();
        membershipBoard_image.setFile_name(request.getImage().getOriginalFilename());

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

        membershipBoard_image.setImagePath(path +uuid + request.getImage().getOriginalFilename());
        membershipBoard.setMembershipBoard_image(membershipBoard_image);

        membershipBoardService.join(membershipBoard);

        return "성공";

    }

    @Data
    @AllArgsConstructor
    static class MembershipBoardRequest{
        private String title;
        private String content;
        private MultipartFile image;
        private String token;
    }

    @GetMapping("/membershipBoardNum/{name}")
    public int getNum(@PathVariable("name") String name){
        List<Membership> memberships = membershipService.findbyName(name);
        Membership membership = memberships.get(0);
        List<MembershipBoard> membershipBoards = membershipBoardService.findByMembershipId(membership.getId());
        return membershipBoards.size();

    }

    @GetMapping("/membershipBoardData/{membershipId}")
    public List<MembershipBoardResponse> getMembershipBoardData(@PathVariable("membershipId") Long id){
        List<MembershipBoard> memberships = membershipBoardService.findByMembershipId(id);

        return memberships.stream()
                .map(MembershipBoardResponse::new)
                .collect(Collectors.toList());

    }

    @GetMapping("/membershipBoardDataByaName/{membershipName}")
    public List<MembershipBoardResponse> getMembershipBoardDataName(@PathVariable("membershipName") String name){

        List<MembershipBoard> memberships = membershipBoardService.findByMembershipName(name);

        return memberships.stream()
                .map(MembershipBoardResponse::new)
                .collect(Collectors.toList());

    }

    @Data
    @AllArgsConstructor
    static class MembershipBoardResponse{
        private String token;
        private Long ImageId;

        private String title;

//        private String content;

        private int month;
        private int day;
        public MembershipBoardResponse(MembershipBoard m){
            this.ImageId = m.getMembershipBoard_image().getId();
            this.title =m.getTitle();
//            this.content = m.getContent();
            this.month = m.getLocalDateTime().getMonthValue();
            this.day = m .getLocalDateTime().getDayOfMonth();
            this.token = m.getMember().getToken();
        }
    }


    @GetMapping("/membershipBoardImage/{id}")
    public ResponseEntity<Resource> getBoardImageId(@PathVariable("id") Long id) {



        MembershipBoard findMembershipBoard = membershipBoardService.findOne(id);

        try {
            FileSystemResource resource = new FileSystemResource(findMembershipBoard.getMembershipBoard_image().getImagePath());
            if (!resource.exists()) {
                throw new NotFoundImageException();
            }
            HttpHeaders header = new HttpHeaders();
            Path filePath = null;
            filePath = Paths.get(findMembershipBoard.getMembershipBoard_image().getImagePath());
            header.add("Content-Type", Files.probeContentType(filePath));

            return  new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
        } catch (Exception e) {
            throw new NotFoundImageException();
        }
    }


}
