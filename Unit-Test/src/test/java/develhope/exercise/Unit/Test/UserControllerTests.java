package develhope.exercise.Unit.Test;


import com.fasterxml.jackson.databind.ObjectMapper;
import develhope.exercise.Unit.Test.controllers.UserController;
import develhope.exercise.Unit.Test.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles(value = "test")
class UserControllerTests {
    @Autowired
    private UserController userController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void controllerExistTest() {
        assertThat(userController).isNotNull();
    }
    private User createUser() throws Exception {
        User user = new User();
        user.setName("Mario");
        user.setSurname("Rossi");
        user.setEmail("test@gmail.com");
        return createUserb(user);
    }
    private User createUserb(User user) throws Exception {
        MvcResult result = createStudentRequest(user);
        User userMap = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
        assertThat(userMap.getId()).isNotNull();
        assertThat(userMap).isNotNull();
        return userMap;
    }
    private MvcResult createStudentRequest(User user) throws Exception {
        if(user == null) return null;
        String studentJSON = objectMapper.writeValueAsString(user);
        return this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/user/create")
                        .content(studentJSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }
    private User getUserFromID(Long id) throws Exception {
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/get-single/" + id))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        try{
            String studentJSON = result.getResponse().getContentAsString();
            User user = objectMapper.readValue(studentJSON, User.class);
            assertThat(user.getId()).isNotNull();
            assertThat(user).isNotNull();
            return user;
        } catch (Exception e) {
            return null;
        }
    }
    @Test
    void createUserTest() throws Exception {
        User result = createUser();
    }
    @Test
    void readUserListTest() throws Exception {
        createUser();
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/user/get-all"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        List<User> users = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
        System.out.println("Users in list are: " + users.size());
        assertThat(users.size()).isNotZero();
    }
    @Test
    void readSingleUserTest() throws Exception {
        User user = createUser();
        assertThat(user.getId()).isNotNull();
        User userID = getUserFromID(user.getId());
        assertThat(userID.getId()).isEqualTo(user.getId());
    }
    @Test
    void updateNameTest() throws Exception {
        User user = createUser();
        assertThat(user.getId()).isNotNull();
        User updatedUser = userController.updateName(user.getId(), "test");
        assertThat(user.getEmail()).isEqualTo("test");
    }
    @Test
    void deleteSingleUserTest() throws Exception {
        User user = createUser();
        assertThat(user.getId()).isNotNull();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/user/delete/" + user.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        User userID = getUserFromID(user.getId());
        assertThat(userID).isNull();
    }
}