package tps.pruebatecnica.e_commerce.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import tps.pruebatecnica.e_commerce.entities.User;
import tps.pruebatecnica.e_commerce.response.GenericResponse;
import tps.pruebatecnica.e_commerce.services.UserServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserServiceImpl userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldReturnOkWithListOfAllUsers() throws Exception {

        User user = new User(1L, "Juan", "Perez", "juan@example.com", "juan123", "USER", "password123", true, null, null, null, null);
        List<User> users = List.of(user);
        GenericResponse<List<User>> response = new GenericResponse<List<User>>("200", "all users ok", users);
        
        Mockito.when(userService.searchAll()).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(get("/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"))
                .andExpect(jsonPath("$.message").value("all users ok"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").value("Juan"));
    }

    @Test
    public void shouldReturnAuthAdmin() throws Exception {

        String username = "prueba";
        String password = "123456";

        Map<String, String> response = new HashMap<>();
        response.put("role", "admin");

        Mockito.when(userService.authenticate(username, password)).thenReturn(response);


        mockMvc.perform(get("/users/login")
                .param("username", username)
                .param("password", password))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("admin"));

    }

    @Test
    public void shouldReturnUserByIdOk() throws Exception {
        
        Long id= 1L;
        User user = new User(1L, "Juan", "Perez", "juan@example.com", "juan123", "USER", "password123", true, null, null, null, null);
        GenericResponse<User> response = new GenericResponse<User>("200", "user finded", user);

        Mockito.when(userService.searchById(id)).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(get("/users/getbyid").param("id", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"))
                .andExpect(jsonPath("$.message").value("user finded"))
                .andExpect(jsonPath("$.data.name").value("Juan"));

    }

    @Test
    public void RegisterUserOk() throws Exception {

        User user = new User(null, "Juan", "Perez", "juan@example.com", "juan123", "USER", "password123", true, null, null, null, null);
        User userCreated = new User(1L, "Juan", "Perez", "juan@example.com", "juan123", "USER", "password123", true, null, null, null, null);

        GenericResponse<User> response = new GenericResponse<User>("200", "User registered", userCreated);

        Mockito.when(userService.saveUser(user)).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"))
                .andExpect(jsonPath("$.message").value("User registered"))
                .andExpect(jsonPath("$.data.name").value("Juan"));
    }

    @Test
    public void shouldReturnDeletedUserOk() throws Exception{

        Long id= 1L;
        User userDeleted = new User(1L, "Juan", "Perez", "juan@example.com", "juan123", "USER", "password123", false, null, null, null, null);

        GenericResponse<User> response = new GenericResponse<User>("200", "User deleted", userDeleted);

        Mockito.when(userService.deleteUser(id)).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(delete("/users/delete").param("id", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"))
                .andExpect(jsonPath("$.message").value("User deleted"))
                .andExpect(jsonPath("$.data.status").value("false"));
    }

    @Test
    public void shouldReturnUpdatedUserOk() throws Exception{

        Long id= 1L;
        User user = new User(1L, "Juan", "Perez", "juan@example.com", "juan123", "USER", "password123", false, null, null, null, null);
        User userUpdated = new User(1L, "David", "Perez", "juan@example.com", "juan123", "USER", "password123", false, null, null, null, null);

        GenericResponse<User> response = new GenericResponse<User>("200", "User updated", userUpdated);

        Mockito.when(userService.updateUser(id, user)).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(put("/users/update")
                .param("id", id.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"))
                .andExpect(jsonPath("$.message").value("User updated"))
                .andExpect(jsonPath("$.data.name").value("David"));
    }

    
}