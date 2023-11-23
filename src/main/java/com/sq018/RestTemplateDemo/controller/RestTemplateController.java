package com.sq018.RestTemplateDemo.controller;

import com.sq018.RestTemplateDemo.model.Post;
import com.sq018.RestTemplateDemo.service.RestTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/rest-template/")
@RequiredArgsConstructor
@RestController
public class RestTemplateController {

    private final RestTemplateService restTemplateService;

    @GetMapping("/ping")
    public ResponseEntity<?> ping() {
        return new ResponseEntity<>("We are live", HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getPosts() {
        return restTemplateService.getAllPosts();
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getPostById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(restTemplateService.getPostById(id), HttpStatus.OK);
    }

    @PostMapping("/posts")
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        return restTemplateService.createPost(post);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<?> updatePostById(@PathVariable("id") Long id, @RequestBody Post post) {
        return restTemplateService.updatePost(id, post);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Long id) {
        restTemplateService.deletePost(id);
        return new ResponseEntity<>("Delete successful", HttpStatus.NO_CONTENT);
    }
}
