package com.sq018.RestTemplateDemo.service;

import com.sq018.RestTemplateDemo.model.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RestTemplateServiceImplementation implements RestTemplateService {

    @Value("${jsonplaceholder.url}")
    private String url;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ResponseEntity<?> getAllPosts() {
        return restTemplate.getForEntity(url + "/posts", List.class);
    }

    @Override
    public Post getPostById(Long id) {
        String path = url + "/posts/" + id;
        return restTemplate.getForObject(path, Post.class, id);
    }

    @Override
    public ResponseEntity<?> createPost(Post post) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Post> requestEntity = new HttpEntity<>(post, headers);

        return restTemplate.postForEntity(url + "/posts", requestEntity, Post.class);
    }

    @Override
    public ResponseEntity<?> updatePost(Long id, Post post) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Post> requestEntity = new HttpEntity<>(post, headers);
        String path = url + "/posts/" + id;

        try {
            restTemplate.put(path, requestEntity, Post.class);
            return new ResponseEntity<>("Update successful", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.toString());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deletePost(Long id) {
        String path = url + "/posts/" + id;

        try {
            restTemplate.put(path, id);
        } catch (Exception e) {
            log.error(e.toString());
        }
    }
}
