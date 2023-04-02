package com.baekjo.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Member_Image {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "member_image_id")
    private Long id;

    private String file_name;

    private String imagePath;

    @OneToOne(mappedBy = "member_image" , fetch = FetchType.LAZY)
    private Member member;
}