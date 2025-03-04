package tps.pruebatecnica.e_commerce.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import tps.pruebatecnica.e_commerce.dao.IUserDao;
import tps.pruebatecnica.e_commerce.entities.User;
import tps.pruebatecnica.e_commerce.response.GenericResponse;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceImplTest {
    
    @Mock
    private IUserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void shouldReturnAllUsersWhenExist() {

        User user = new User(1L, "Juan", "Perez", "juan@example.com", "juan123", "USER", "password123", true, null, null, null, null);
        List<User> users = List.of(user);
        
        Mockito.when(userDao.findAll()).thenReturn(users);

        ResponseEntity<GenericResponse<List<User>>> response = userService.searchAll();

        assertNotNull(response);
        assertEquals(users, response.getBody().getData());
    }

    @Test
    public void shouldReturnUserByIdExist() {

        Long id = 1L;
        User user = new User(1L, "Juan", "Perez", "juan@example.com", "juan123", "USER", "password123", true, null, null, null, null);

        Mockito.when(userDao.findById(id)).thenReturn(Optional.of(user));

        ResponseEntity<GenericResponse<User>> response = userService.searchById(id);
        
        assertNotNull(response);
        assertEquals(user, response.getBody().getData());
    }

    @Test
    public void shouldReturnUserWhenIsCreated() {

        User user = new User(null, "Juan", "Perez", "juan@example.com", "juan123", "USER", "password123", true, null, null, null, null);
        User userCreated = new User(1L, "Juan", "Perez", "juan@example.com", "juan123", "USER", "password123", true, null, null, null, null);

        Mockito.when(userDao.save(user)).thenReturn(userCreated);

        ResponseEntity<GenericResponse<User>> response = userService.saveUser(user);

        assertNotNull(response);
        assertEquals(userCreated.getName(), response.getBody().getData().getName());

    }

    @Test
    public void shouldReturnUserWhenIsUpdated() {

        Long id= 1L;
        User user = new User(1L, "Juan", "Perez", "juan@example.com", "juan123", "USER", "password123", false, null, null, null, null);
        User userUpdated = new User(1L, "David", "Perez", "juan@example.com", "juan123", "USER", "password123", false, null, null, null, null);

        Mockito.when(userDao.findById(id)).thenReturn(Optional.of(user));
        Mockito.when(userDao.save(user)).thenReturn(userUpdated);

        ResponseEntity<GenericResponse<User>> response = userService.updateUser(id,user);

        assertNotNull(response);
        assertNotEquals(user, response.getBody().getData());

    }

    @Test
    public void shouldReturnNotFoundUserWhenTryToUpdate() {

        Long id= 1L;
        User user = new User(1L, "Juan", "Perez", "juan@example.com", "juan123", "USER", "password123", false, null, null, null, null);
        
        Mockito.when(userDao.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<GenericResponse<User>> response = userService.updateUser(id,user);

        assertNotNull(response);
        assertEquals(null, response.getBody().getData());
    }

    @Test
    public void shouldReturnUserWhenLogicalDeleted() {
        Long id = 1L;
        User user = new User(1L, "Juan", "Perez", "juan@example.com", "juan123", "USER", "password123", true, null, null, null, null);
        User userDeleted = new User(1L, "Juan", "Perez", "juan@example.com", "juan123", "USER", "password123", false, null, null, null, null);

        Mockito.when(userDao.findById(id)).thenReturn(Optional.of(user));

        Mockito.when(userDao.save(user)).thenReturn(userDeleted);

        ResponseEntity<GenericResponse<User>> response = userService.deleteUser(id);

        assertNotNull(response);
        assertFalse(user.getStatus());
        assertFalse(response.getBody().getData().getStatus());
    }

    @Test
    public void shouldReturnNotFoundUserWhenTryToDelete() {

        Long id= 1L;
        Mockito.when(userDao.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<GenericResponse<User>> response = userService.deleteUser(id);

        assertNotNull(response);
        assertEquals(null, response.getBody().getData());
    }

    @Test
    public void shouldReturnRoleWhenUserAuthIsOk() {
        
        String username = "juan123";
        String password = "password123";

        User user = new User(1L, "Juan", "Perez", "juan@example.com", "juan123", "USER", "password123", true, null, null, null, null);

        user.setPassword( new BCryptPasswordEncoder().encode(password));
        
        Map<String, String> responseExpected = new HashMap<>();
        responseExpected.put("role", user.getRole());

        Mockito.when(userDao.findByUsername(username)).thenReturn(user);

        Map<String, String> response = userService.authenticate(username, password);

        assertNotNull(response);
        assertEquals(responseExpected, response);

    }

    @Test
    public void shouldReturnRoleWhenUserAuthIsNok() {
        
        String username = "juan123";
        String password = "password123";

        User user = new User(1L, "Juan", "Perez", "juan@example.com", "juan123", "USER", "password123", true, null, null, null, null);

        Map<String, String> responseExpected = new HashMap<>();
        responseExpected.put("role", user.getRole());

        Mockito.when(userDao.findByUsername(username)).thenReturn(user);

        Map<String, String> response = userService.authenticate(username, password);

        assertNotNull(response);
        assertNotEquals(responseExpected, response);

    }
    @Test
    public void shouldReturnRoleWhenUserAuthButPassIsNok() {
        
        String username = "juan123";
        String password = "password12356";

        User user = new User(1L, "Juan", "Perez", "juan@example.com", "juan123", "USER", "password123", true, null, null, null, null);

        user.setPassword( new BCryptPasswordEncoder().encode(password));
        Map<String, String> responseExpected = new HashMap<>();
        responseExpected.put("role", user.getRole());

        Mockito.when(userDao.findByUsername(username)).thenReturn(user);

        Map<String, String> response = userService.authenticate(username, password);

        System.out.println(response);
        assertNotNull(response);
        assertNotEquals(responseExpected, response);

    }

}
