package com.sq018.RestTemplateDemo.service;

import com.sq018.RestTemplateDemo.model.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestTemplateService {
    ResponseEntity<?> getAllPosts();

    Post getPostById(Long id);

    ResponseEntity<?> createPost(Post post);

    ResponseEntity<?> updatePost(Long id, Post post);

    void deletePost(Long id);
}
