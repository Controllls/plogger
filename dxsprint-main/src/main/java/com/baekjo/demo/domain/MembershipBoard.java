package com.baekjo.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class MembershipBoard {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "membershipBoard_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    private LocalDateTime localDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_id")
    private Membership membership;

    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "membershipBoard_image_id")
    private MembershipBoard_Image membershipBoard_image;



}
