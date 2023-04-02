package com.baekjo.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
//    private String number;
    private String name;
    private String nickname;

    @Column(unique = true)
    private String email;
    private String password;

    private String token;

    private Long age;
    private Float tall;
    private Float weight;

    private Long num;

//    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
//    @JoinColumn(name = "membership_id")
//    private Membership membership;

    private Long membershipId;


    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @OneToMany(mappedBy = "member" ,fetch = FetchType.LAZY)
    private List<Plog> plogs = new ArrayList<>();

    @OneToMany(mappedBy = "member" ,fetch = FetchType.LAZY)
    private List<MembershipBoard> membershipBoard;

    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "member_image_id")
    private Member_Image member_image;




}
