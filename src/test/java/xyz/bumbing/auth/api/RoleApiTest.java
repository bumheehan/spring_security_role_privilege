package xyz.bumbing.auth.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoleApiTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper om;

    @BeforeAll
    public void init() {
        om = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Role 추가")
    public void createRole() {

    }

    @Test
    @DisplayName("Role 수정")
    public void updateRole() {

    }

    @Test
    @DisplayName("Role 삭제")
    public void deleteRole() {

    }

    @Test
    @DisplayName("Privilege  추가")
    public void createPrivilege() {

    }

    @Test
    @DisplayName("Privilege 수정")
    public void updatePrivilege() {

    }

    @Test
    @DisplayName("Privilege 삭제")
    public void deletePrivilege() {

    }

}