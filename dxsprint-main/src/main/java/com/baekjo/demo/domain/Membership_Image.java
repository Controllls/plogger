package com.baekjo.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter @Getter
public class Membership_Image {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "membership_image_id")
    private Long id;

    private String file_name;

    private String imagePath;

    @OneToOne(mappedBy = "membership_image"  ,fetch = FetchType.LAZY)
    private Membership membership;

}
