package tps.pruebatecnica.e_commerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tps.pruebatecnica.e_commerce.entities.User;

public interface IUserDao extends JpaRepository<User, Long>{

    User findByUsername(String username);
}
