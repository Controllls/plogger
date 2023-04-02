package com.baekjo.demo.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class CertificationBoard {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "certificationBoard_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    private LocalDateTime localDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_id")
    private Membership membership;

    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "certificationBoard_image_id")
    private CertificationBoard_Image certificationBoard_image;



}
