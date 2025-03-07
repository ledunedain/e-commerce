package tps.pruebatecnica.e_commerce.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import tps.pruebatecnica.e_commerce.dao.IUserDao;
import tps.pruebatecnica.e_commerce.entities.User;
import tps.pruebatecnica.e_commerce.response.GenericResponse;

@Service
public class UserServiceImpl implements IUserService{

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private IUserDao userDao;

    public UserServiceImpl() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    
    @Override
    public ResponseEntity<GenericResponse<List<User>>> searchAll() {
        
        Optional<List<User>> users = Optional.of(userDao.findAll());

        return users
            .map(user -> ResponseEntity.ok(new GenericResponse<>("200", "all users ok", user)))
            .orElse(ResponseEntity.status(500).body(new GenericResponse<>("500", "Error getting users", null)));
    }

    @Override
    public ResponseEntity<GenericResponse<User>> searchById(Long id) {

        Optional<User> userFind =userDao.findById(id);

        return userFind
            .map(user -> ResponseEntity.ok(new GenericResponse<>("200", "user finded", user)))
            .orElse(ResponseEntity.status(404).body(new GenericResponse<>("404", "user not finded", null)));
    }

    @Override
    public ResponseEntity<GenericResponse<User>> saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
    
        Optional<User> userOptional = Optional.ofNullable(userDao.save(user));
    
        return userOptional
            .map(savedUser -> ResponseEntity.ok(new GenericResponse<>("200", "User registered", savedUser)))
            .orElse(ResponseEntity.status(500).body(new GenericResponse<>("500", "Error saving user", null)));
    }
    

    @Override
    public ResponseEntity<GenericResponse<User>> updateUser(Long id, User user) {
        
        Optional<User> findUser = userDao.findById(id);

        if( findUser.isEmpty()){
            return ResponseEntity.status(404).body(new GenericResponse<>("404", "user not found", null));
        }

        user.setId(findUser.get().getId());

        User userUptaded = userDao.save(user);

        return ResponseEntity.ok(new GenericResponse<>("200", "user updated", userUptaded));
    }

    @Override
    public ResponseEntity<GenericResponse<User>> deleteUser(Long id) {
       
        Optional<User> findUser = userDao.findById(id);

        if (findUser.isEmpty()) {
            return ResponseEntity.status(404).body(new GenericResponse<>("404", "user not found", null));
        }

        findUser.get().setStatus(false);
        User updatedUser = userDao.save(findUser.get());

        return ResponseEntity.ok(new GenericResponse<>("200", "user deleted", updatedUser));
}


    public Map<String, String> authenticate(String username, String password) {
        
        Optional<User> user = Optional.of(userDao.findByUsername(username));
        Map<String, String> response = new HashMap<>();
        if( user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())){
            response.put("role", user.get().getRole());
            return response;
        }
        response.put("role", "notExist");
        return response;
    }
}
