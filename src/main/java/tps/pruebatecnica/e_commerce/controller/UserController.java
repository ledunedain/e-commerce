package tps.pruebatecnica.e_commerce.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tps.pruebatecnica.e_commerce.entities.User;
import tps.pruebatecnica.e_commerce.response.GenericResponse;
import tps.pruebatecnica.e_commerce.services.UserServiceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<GenericResponse<User>> registerUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/login")
    public Map<String, String> login(@RequestParam String username, @RequestParam String password) {
        return userService.authenticate(username, password);
    }

    @GetMapping("/all")
    public ResponseEntity<GenericResponse<List<User>>> getAllUser() {
        return userService.searchAll();
    }

    @GetMapping("/getbyid")
    public ResponseEntity<GenericResponse<User>> getUserById(@RequestParam Long id) {
        return userService.searchById(id);
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<GenericResponse<User>> deleteUserById (@RequestParam Long id){
        return userService.deleteUser(id);
    }

    @PutMapping("/update")
    public ResponseEntity<GenericResponse<User>> updateUser(@RequestParam Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }
    
}
