package com.baekjo.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Membership {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "membership_id")
    private Long id;
    private String name;
    private Long Number;
    private String content;
    private String leader;
    private String rule;
    private String tip;


    @OneToMany(mappedBy = "membership"  ,fetch = FetchType.LAZY)
    private List<MembershipBoard> membershipBoards = new ArrayList<>();

//    @OneToMany(mappedBy = "membership")
//    private List<Membership_Image> membership_images = new ArrayList<>();


    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "membership_image_id")
    private Membership_Image membership_image;

}
