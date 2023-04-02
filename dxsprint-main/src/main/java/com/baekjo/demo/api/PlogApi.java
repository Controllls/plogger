package com.baekjo.demo.api;


import com.baekjo.demo.domain.Member;
import com.baekjo.demo.domain.MemberStatus;
import com.baekjo.demo.domain.Plog;
import com.baekjo.demo.domain.Plog_Image;
import com.baekjo.demo.exception.NotFoundImageException;
import com.baekjo.demo.service.MemberService;
import com.baekjo.demo.service.PlogService;
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
import java.time.Month;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PlogApi {
    private final PlogService plogService;
    private final MemberService memberService;

    //todo 1달치 데이터 보내기
    @GetMapping("/monthData/{token}")
    private MonthPlogResponse sendMonthData(@PathVariable("token") String token){
        List<Plog> plogs = plogService.findByToken(token);
        List<Member> members = memberService.findByToken(token);
        Member member = members.get(0);

        LocalDateTime now = LocalDateTime.now();
        Month month = now.getMonth();
        Float sumDistance = 0F;
        int sumTime = 0;
        int sumCalorie = 0;
        Long sticker = member.getNum();

        for (Plog selectPlog : plogs) {
            if (month == selectPlog.getLocalDateTime().getMonth()){
                sumDistance += selectPlog.getDistance();
                sumTime += selectPlog.getTime();
                sumCalorie += selectPlog.getCalorie();

            }
        }


        return new MonthPlogResponse(sumDistance, sumTime, sumCalorie ,sticker );

    }

    @Data
    @AllArgsConstructor
    static class MonthPlogResponse{
        private Float distance;
        private int time;
        private int calorie;
        private Long sticker;
    }
//    @Data
//    @AllArgsConstructor
//    static class MonthPlogRequest{
//        private String token;
//
//    }



    //todo 완료 버튼 클릭시 데이터 저장
    @PostMapping("/save/plog/sticker")
    private String savePlogSticker(PlogRequestCerti plogRequest){
        Plog plog = new Plog();
        plog.setCalorie(plogRequest.getCalorie());
        plog.setDistance(plogRequest.getDistance());
        plog.setTime(plogRequest.getTime());
        plog.setLocalDateTime(LocalDateTime.now());

        List<Member> members = memberService.findByToken(plogRequest.getToken());

        Member member = members.get(0);
        member.setNum(member.getNum() +1);

        plog.setSticker(1L);

        Plog_Image plog_image = new Plog_Image();

        String path = "/home/ec2-user/"+plogRequest.getToken()+"/";
//        String path = "/Users/choejong-geun/Desktop/images/"+plogRequest.getToken()+"/";
        Path imagePath = Paths.get(path + plogRequest.getImage().getOriginalFilename());
        //사진 서버에 저장
        try {
            Files.write(imagePath, plogRequest.getImage().getBytes());
        } catch (Exception e) {
            log.info("사진 저장 실패");
            return "실패";
        }

        plog_image.setImagePath(path + plogRequest.getImage().getOriginalFilename());
        plog_image.setFile_name(plogRequest.getImage().getOriginalFilename());
        plog.setPlog_image(plog_image);

        /***
        path = "/home/ec2-user/"+plogRequest.getToken()+"/";
//        path = "/Users/choejong-geun/Desktop/images/"+plogRequest.getToken()+"/";
        imagePath = Paths.get(path + plogRequest.getImageCerti().getOriginalFilename());
        //사진 서버에 저장
        try {
            Files.write(imagePath, plogRequest.getImageCerti().getBytes());
        } catch (Exception e) {
            log.info("사진 저장 실패");
            return "실패";
        }

        Plog_Certification_Image plog_certification_image = new Plog_Certification_Image();

        plog_certification_image.setImagePath(path + plogRequest.getImageCerti().getOriginalFilename());
        plog_certification_image.setFile_name(plogRequest.getImageCerti().getOriginalFilename());
        plog.setPlog_certification_image(plog_certification_image);
        */

        members = memberService.findByToken(plogRequest.getToken());
        plog.setMember(members.get(0));

        memberService.update(member);
        plogService.join(plog);
        return "성공";
    }

    //todo 완료 버튼 클릭시 데이터 저장 (그냥 완료)
    @PostMapping("/save/plog")
    private String savePlog(PlogRequest plogRequest){
        Plog plog = new Plog();
        plog.setCalorie(plogRequest.getCalorie());
        plog.setDistance(plogRequest.getDistance());
        plog.setTime(plogRequest.getTime());
        plog.setLocalDateTime(LocalDateTime.now());
        plog.setSticker(0L);

        Plog_Image plog_image = new Plog_Image();

        String uuid = UUID.randomUUID().toString().replace("-", "");
        String path = "/home/ec2-user/"+plogRequest.getToken()+"/";
//        String path = "/Users/choejong-geun/Desktop/images/"+plogRequest.getToken()+"/";
        Path imagePath = Paths.get(path + uuid + plogRequest.getImage().getOriginalFilename());


        //사진 서버에 저장
        try {
            Files.write(imagePath, plogRequest.getImage().getBytes());
        } catch (Exception e) {
            log.info("사진 저장 실패");
            return "실패";
        }

        plog_image.setImagePath(path + uuid + plogRequest.getImage().getOriginalFilename());
        plog_image.setFile_name(plogRequest.getImage().getOriginalFilename());
        plog.setPlog_image(plog_image);

        List<Member> members = memberService.findByToken(plogRequest.getToken());
        plog.setMember(members.get(0));

        plogService.join(plog);
        return "성공";
    }

    @Data
    @AllArgsConstructor
    static class PlogRequest{
        private String token;
        private Float distance;
        private Long time;
        private Float calorie;
        private MultipartFile image;
    }

    @Data
    @AllArgsConstructor
    static class PlogRequestCerti{
        private String token;
        private Float distance;
        private Long time;
        private Float calorie;
        private MultipartFile image;
        private MultipartFile imageCerti;
    }

    //todo plog사진 넘겨주기
    @GetMapping("/image/plog/{userToken}/{id}")
    public ResponseEntity<Resource> getImage(@PathVariable("userToken") String userToken,
                                             @PathVariable("id") int id) {

        List<Plog> findFlogs = plogService.findByToken(userToken);

        Plog plog = findFlogs.get(id);

        try {
            FileSystemResource resource = new FileSystemResource(plog.getPlog_image().getImagePath());
            if (!resource.exists()) {
                throw new NotFoundImageException();
            }
            HttpHeaders header = new HttpHeaders();
            Path filePath = null;
            filePath = Paths.get(plog.getPlog_image().getImagePath());
            header.add("Content-Type", Files.probeContentType(filePath));

            return  new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
        } catch (Exception e) {
            throw new NotFoundImageException();
        }
    }


    /*
        plog 사진 넘겨주기
     */
    @GetMapping("/image/plog/{userToken}/{year}/{month}/{day}/{hour}/{min}")
    public ResponseEntity<Resource> getImageByDate(@PathVariable("userToken") String userToken,
                                                 @PathVariable("year") int year,
                                                 @PathVariable("month") int month,
                                                 @PathVariable("day") int day,
                                                 @PathVariable("hour") int hour,
                                                   @PathVariable("min") int min) {

        List<Plog> findFlogs = plogService.findByToken(userToken);
        Plog plog = null;
        for (Plog findPlog : findFlogs) {
            if(findPlog.getLocalDateTime().getYear() == year
                && findPlog.getLocalDateTime().getMonthValue() == month
                && findPlog.getLocalDateTime().getDayOfMonth() == day
                && findPlog.getLocalDateTime().getHour() == hour
                && findPlog.getLocalDateTime().getMinute() == min){
                    plog = findPlog;
            }
        }
        if (plog == null){
            return null;
        }

        try {
            FileSystemResource resource = new FileSystemResource(plog.getPlog_image().getImagePath());
            if (!resource.exists()) {
                throw new NotFoundImageException();
            }
            HttpHeaders header = new HttpHeaders();
            Path filePath = null;
            filePath = Paths.get(plog.getPlog_image().getImagePath());
            header.add("Content-Type", Files.probeContentType(filePath));

            return  new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
        } catch (Exception e) {
            throw new NotFoundImageException();
        }
    }


    @GetMapping("/runningMember")
    public List<String> getRunning(){
        List<String> all = memberService.findRunning(MemberStatus.RUN);

        return all;

    }

    @GetMapping("/runningMember/{token}")
    public List<String> getRunning(@PathVariable("token") String token){
        List<Member> members =  memberService.findByToken(token);
        Member member = members.get(0);


        return memberService.findMembershipRunning(member.getMembershipId() ,MemberStatus.RUN);

    }





    @GetMapping("/run/{token}")
    public void run(@PathVariable("token") String token){
        List<Member> members = memberService.findByToken(token);
        members.get(0).setStatus(MemberStatus.RUN);


        memberService.update(members.get(0));
    }

    @GetMapping("/stop/{token}")
    public void stop(@PathVariable("token") String token){
        List<Member> members = memberService.findByToken(token);
        members.get(0).setStatus(MemberStatus.STAY);


        memberService.update(members.get(0));
    }

    @GetMapping("/plogData/{token}")
    public List<PlogResponse> getPlogInfo(@PathVariable("token") String token){
        List<Plog> plogs = plogService.findByToken(token);

        return plogs.stream()
                .map(PlogResponse::new)
                .collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    static class PlogResponse{
        private Float distance;
        private Long time;
        private Float calorie;

        private int year;
        private int month;
        private int day;
        private int hour;
        private int min;


        public PlogResponse(Plog m) {
            this.distance = m.getDistance();
            this.calorie = m.getCalorie();
            this.time = m.getTime();

            this.year = m.getLocalDateTime().getYear();
            this.month = m.getLocalDateTime().getMonthValue();
            this.day = m.getLocalDateTime().getDayOfMonth();
            this.hour = m.getLocalDateTime().getHour();
            this.min = m.getLocalDateTime().getMinute();
        }
    }

}
