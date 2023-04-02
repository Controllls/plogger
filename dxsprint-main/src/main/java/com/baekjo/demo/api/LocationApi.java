package com.baekjo.demo.api;

import com.baekjo.demo.betch.CSVReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LocationApi {

    @GetMapping("/")
    private String check() {
        return "reponse_ok";
    }

    @GetMapping("/envel/location")
    private List<List<String>> getLocationEn() {
        CSVReader csvReader = new CSVReader();
        List<List<String>> lists = csvReader.readCSV("/home/ec2-user/jong.txt");
        return lists;
    }

    @GetMapping("/envel2/location")
    private List<List<String>> getLocationEn2() {
        CSVReader csvReader = new CSVReader();
        List<List<String>> lists = csvReader.readCSV("/home/ec2-user/jong2.txt");
        return lists;
    }

    @GetMapping("/busan/location")
    private List<List<String>> getLocationBusan() {
        CSVReader csvReader = new CSVReader();
        List<List<String>> lists = csvReader.readCSV("/home/ec2-user/busan.txt");
        return lists;
    }

    @GetMapping("/busan/th/location")
    private List<List<String>> getLocationBusanTh() {
        CSVReader csvReader = new CSVReader();
        List<List<String>> lists = csvReader.readCSV("/home/ec2-user/busanTh.txt");
        return lists;
    }

    @GetMapping("/busan/th2/location")
    private List<List<String>> getLocationBusanTh2() {
        CSVReader csvReader = new CSVReader();
        List<List<String>> lists = csvReader.readCSV("/home/ec2-user/busanTh2.txt");
        return lists;
    }

    @GetMapping("/beach/location")
    private List<List<String>> getLocationBeach() {
        CSVReader csvReader = new CSVReader();
        List<List<String>> lists = csvReader.readCSV("/home/ec2-user/beach.txt");
        return lists;
    }

    @GetMapping("/mountain/location")
    private List<List<String>> getLocationMountain() {
        CSVReader csvReader = new CSVReader();
        List<List<String>> lists = csvReader.readCSV("/home/ec2-user/mountain.txt");
        return lists;
    }

    @GetMapping("/sanchek/location")
    private List<List<String>> getLocationSanchek() {
        CSVReader csvReader = new CSVReader();
        List<List<String>> lists = csvReader.readCSV("/home/ec2-user/sanchek.txt");
        return lists;
    }

    @GetMapping("/park/location")
    private List<List<String>> getLocation() {
        CSVReader csvReader = new CSVReader();
        List<List<String>> lists = csvReader.readCSV("/home/ec2-user/2.txt");
        return lists;
    }
}
