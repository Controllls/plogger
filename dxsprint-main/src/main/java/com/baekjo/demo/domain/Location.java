package com.baekjo.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Location {
    @Id
    @GeneratedValue
    @Column(name = "location_id")
    private Long id;

    private String type;
    private String name;
    private String latitude;
    private String longitude;


}
