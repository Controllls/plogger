package com.baekjo.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Plog {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "plog_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime localDateTime;
    private Float distance;
    private Long time;
    private Float calorie;

    private Long sticker;


    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "plog_image_id")
    private Plog_Image plog_image;

//    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
//    @JoinColumn(name = "plog_certification_image_id")
//    private Plog_Certification_Image plog_certification_image;


}
