package com.baekjo.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter @Getter
public class MembershipBoard_Image {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "membershipBoard_image_id")
    private Long id;

    private String file_name;

    private String imagePath;

    @OneToOne(mappedBy = "membershipBoard_image" ,fetch = FetchType.LAZY)
    private MembershipBoard membershipBoard;
}
