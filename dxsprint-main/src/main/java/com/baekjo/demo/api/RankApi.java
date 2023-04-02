package com.baekjo.demo.api;

import com.baekjo.demo.domain.Member;
import com.baekjo.demo.service.MemberService;
import com.baekjo.demo.service.PlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RankApi {
    private final PlogService plogService;
    private final MemberService memberService;

    //todo rank All
    @GetMapping("/rankAll/{token}")
    public List<String> getRankAll(@PathVariable("token") String token){
        List<String> rank = memberService.findRank();

        int index = 0;
        for (int i = 0 ; i< rank.size() ; i++){
            if (Objects.equals(rank.get(i), token)){
                index = i;
            }
        }


        if (rank.size() >= 3) {
            rank = rank.subList(0, 3);
        }

        rank.add(String.valueOf(index+1));


        return rank;
    }


    @GetMapping("/rankMembership/{token}")
    public List<String> getRankMembership(@PathVariable("token") String token){
        List<Member> byToken = memberService.findByToken(token);
        Member member = byToken.get(0);

        List<String> rank = memberService.findRankMembership(member.getMembershipId());

        int index = 0;
        for (int i = 0 ; i< rank.size() ; i++){
            if (Objects.equals(rank.get(i), token)){
                index = i;
            }
        }


        if (rank.size() >= 3) {
            rank = rank.subList(0, 3);
        }
        rank.add(String.valueOf(index+1));

        return rank;
    }
}
