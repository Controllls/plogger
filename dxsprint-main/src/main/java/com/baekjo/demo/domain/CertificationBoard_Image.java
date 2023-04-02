package com.baekjo.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Setter
@Getter
public class CertificationBoard_Image {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "certificationBoard_image_id")
    private Long id;

    private String file_name;

    private String imagePath;

    @OneToOne(mappedBy = "certificationBoard_image" ,fetch = FetchType.LAZY)
    private CertificationBoard certificationBoard;

}
