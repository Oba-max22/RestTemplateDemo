package com.sq018.RestTemplateDemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sq018.RestTemplateDemo.controller.RestTemplateController;
import com.sq018.RestTemplateDemo.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class RestTemplateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestTemplateController restTemplateControllerInTest;

    @Test
    void init() throws Exception {
        assertThat(restTemplateControllerInTest).isNotNull();
    }

    @Test
    void shouldReturnPingMessage() throws Exception {
        this.mockMvc.perform(get("/rest-template/ping"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("We are live"));
    }

    @Test
    void createPostsAPI() throws Exception {
        Post post = new Post();
        post.setTitle("Real title");
        post.setBody("Real body");
        post.setUserId(1L);

        this.mockMvc.perform(post("/rest-template/posts")
                        .content(asJsonString(post))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=utf-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Real title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.body").value("Real body"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
