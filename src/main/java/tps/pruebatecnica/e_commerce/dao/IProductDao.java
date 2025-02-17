package tps.pruebatecnica.e_commerce.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tps.pruebatecnica.e_commerce.entities.Product;

public interface IProductDao extends JpaRepository< Product , Long>{

    Optional<List<Product>> findByNameContaining(String name);
    
}
