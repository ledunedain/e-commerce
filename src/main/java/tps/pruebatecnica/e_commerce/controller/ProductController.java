package tps.pruebatecnica.e_commerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tps.pruebatecnica.e_commerce.entities.Product;
import tps.pruebatecnica.e_commerce.response.GenericResponse;
import tps.pruebatecnica.e_commerce.services.ProductServiceImpl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/products")
public class ProductController {
    
    private final ProductServiceImpl productService;

    public ProductController( ProductServiceImpl productService){
        this.productService = productService;
    }

    @PostMapping("/register")
    public ResponseEntity<GenericResponse<Product>> registerProduct(@RequestBody Product product) {
        
        return productService.saveProduct(product);
    }

    @GetMapping("/all")
    public ResponseEntity<GenericResponse<List<Product>>> getAllProducts() {
        return productService.searchAll();
    }

    @PutMapping("update")
    public ResponseEntity<GenericResponse<Product>> updateProduc(@RequestParam Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }
    
    
}
