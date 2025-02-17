package tps.pruebatecnica.e_commerce.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import tps.pruebatecnica.e_commerce.entities.Product;
import tps.pruebatecnica.e_commerce.response.GenericResponse;

public interface IProductService {
    
    ResponseEntity<GenericResponse<List<Product>>> searchAll();

    ResponseEntity<GenericResponse<List<Product>>> searchByName(String name);

    ResponseEntity<GenericResponse<Product>> saveProduct( Product product);

    ResponseEntity<GenericResponse<Product>> updateProduct (Long id, Product product);

    ResponseEntity<GenericResponse<Product>> deleteProduct (Long id);
}
