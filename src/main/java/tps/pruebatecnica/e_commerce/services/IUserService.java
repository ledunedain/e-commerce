package tps.pruebatecnica.e_commerce.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import tps.pruebatecnica.e_commerce.entities.User;
import tps.pruebatecnica.e_commerce.response.GenericResponse;

/**
 * IUserService
 */
public interface IUserService {

    ResponseEntity<GenericResponse<List<User>>> searchAll();

    ResponseEntity<GenericResponse<User>> searchById(Long id);

    ResponseEntity<GenericResponse<User>> saveUser(User user);

    ResponseEntity<GenericResponse<User>> updateUser(Long id, User user);

    ResponseEntity<GenericResponse<User>> deleteUser(Long id);

}