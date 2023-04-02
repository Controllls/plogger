package com.baekjo.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Plog_Image {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "plog_image_id")
    private Long id;

    private String file_name;

    private String imagePath;

    @OneToOne(mappedBy = "plog_image" , fetch = FetchType.LAZY)
    private Plog plog;
}