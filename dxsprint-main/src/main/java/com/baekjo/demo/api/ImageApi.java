package com.baekjo.demo.api;

import com.baekjo.demo.domain.Plog;
import com.baekjo.demo.domain.Plog_Image;
import com.baekjo.demo.exception.NotFoundImageException;
import com.baekjo.demo.service.PlogService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImageApi {
    private final PlogService plogService;

    @GetMapping("/image/{userToken}")
    public ResponseEntity<Resource> getImage(@PathVariable("userToken") String userToken){
        List<Plog> findPlogs = plogService.findByToken(userToken);

        Plog findPlog = findPlogs.get(0);
        Plog_Image image = findPlog.getPlog_image();

        try {
            FileSystemResource resource = new FileSystemResource(image.getImagePath());
            if (!resource.exists()) {
                throw new NotFoundImageException();
            }
            HttpHeaders header = new HttpHeaders();
            Path filePath = null;
            filePath = Paths.get(image.getImagePath());
            header.add("Content-Type", Files.probeContentType(filePath));

            return  new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
        } catch (Exception e) {
            throw new NotFoundImageException();
        }
    }

    @Data
    @AllArgsConstructor
    static class ImageRequest {
        private String path;

    }


}
