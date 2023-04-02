package com.baekjo.demo.api;


import com.baekjo.demo.domain.Member;
import com.baekjo.demo.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginApi {
    private final MemberService memberService;


    public boolean matchesBcrypt(String planeText, String hashValue, int strength) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(strength);
        return passwordEncoder.matches(planeText, hashValue);
    }

    public String encodeBcrypt(String planeText, int strength) {
        return new BCryptPasswordEncoder(strength).encode(planeText);
    }


    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        List<Member> byEmail = memberService.findByEmail(request.getEmail());
        Member be = byEmail.get(0);
        if (matchesBcrypt(request.getPassword(), be.getPassword(), 10)) {
            return new LoginResponse(be.getToken());
        } else {
            return null;
        }

    }


    @Data
    @AllArgsConstructor
    static class LoginResponse {
        private String identity;

    }

    @Data
    @AllArgsConstructor
    static class LoginRequest {
        private String email;
        private String password;

    }
}
