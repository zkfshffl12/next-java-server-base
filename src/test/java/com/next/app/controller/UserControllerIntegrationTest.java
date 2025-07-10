package com.next.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.next.app.entity.User;
import com.next.app.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        userRepository.deleteAll();
    }

    @Test
    void createAndGetUser_IntegrationTest() throws Exception {
        // Given
        User user = new User();
        user.setEmail("test@example.com");
        user.setName("테스트 사용자");
        user.setPhone("010-1234-5678");

        // When & Then - 사용자 생성
        String response = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.name").value("테스트 사용자"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // 생성된 사용자의 ID 추출
        User createdUser = objectMapper.readValue(response, User.class);
        Long userId = createdUser.getId();

        // When & Then - 생성된 사용자 조회
        mockMvc.perform(get("/api/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.name").value("테스트 사용자"));
    }

    @Test
    void getAllUsers_IntegrationTest() throws Exception {
        // Given
        User user1 = new User();
        user1.setEmail("user1@example.com");
        user1.setName("사용자1");
        user1.setPhone("010-1111-1111");

        User user2 = new User();
        user2.setEmail("user2@example.com");
        user2.setName("사용자2");
        user2.setPhone("010-2222-2222");

        userRepository.save(user1);
        userRepository.save(user2);

        // When & Then
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].email").value("user1@example.com"))
                .andExpect(jsonPath("$[1].email").value("user2@example.com"));
    }

    @Test
    void updateUser_IntegrationTest() throws Exception {
        // Given
        User user = new User();
        user.setEmail("update@example.com");
        user.setName("원본 이름");
        user.setPhone("010-0000-0000");

        User savedUser = userRepository.save(user);
        Long userId = savedUser.getId();

        // When & Then - 사용자 수정
        User updateData = new User();
        updateData.setName("수정된 이름");
        updateData.setPhone("010-9999-9999");

        mockMvc.perform(put("/api/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value("수정된 이름"))
                .andExpect(jsonPath("$.phone").value("010-9999-9999"));
    }

    @Test
    void deleteUser_IntegrationTest() throws Exception {
        // Given
        User user = new User();
        user.setEmail("delete@example.com");
        user.setName("삭제할 사용자");
        user.setPhone("010-0000-0000");

        User savedUser = userRepository.save(user);
        Long userId = savedUser.getId();

        // When & Then - 사용자 삭제
        mockMvc.perform(delete("/api/users/" + userId))
                .andExpect(status().isOk());

        // 삭제 확인
        mockMvc.perform(get("/api/users/" + userId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUserById_WhenUserNotExists_ShouldReturn404() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound());
    }
} 