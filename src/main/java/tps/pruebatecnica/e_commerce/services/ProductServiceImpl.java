package tps.pruebatecnica.e_commerce.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import tps.pruebatecnica.e_commerce.dao.IProductDao;
import tps.pruebatecnica.e_commerce.entities.Product;
import tps.pruebatecnica.e_commerce.enums.ProductStatus;
import tps.pruebatecnica.e_commerce.response.GenericResponse;

public class ProductServiceImpl implements IProductService{

    @Autowired
    private IProductDao productDao;

    @Override
    public ResponseEntity<GenericResponse<List<Product>>> searchAll() {
        
        Optional<List<Product>> products = Optional.of(productDao.findAll());
    
        return products
            .map(product -> ResponseEntity.ok(new GenericResponse<>("200", "all products", product)))
            .orElse(ResponseEntity.status(500).body(new GenericResponse<>("500", "error getting products", null)));
    }

    @Override
    public ResponseEntity<GenericResponse<List<Product>>> searchByName(String name) {
        
        Optional<List<Product>> productFinded = productDao.findByNameContaining(name);

        return productFinded
            .map(product -> ResponseEntity.ok(new GenericResponse<>("200", "all containing products", product)))
            .orElse(ResponseEntity.status(500).body(new GenericResponse<>("500", "error getting containing products", null)));
    }

    @Override
    public ResponseEntity<GenericResponse<Product>> saveProduct(Product product) {
        
        Optional<Product> productOptional = Optional.ofNullable(productDao.save(product));

        return productOptional
            .map(productSaved -> ResponseEntity.ok(new GenericResponse<>("200", "all containing products", productSaved)))
            .orElse(ResponseEntity.status(500).body(new GenericResponse<>("500", "error getting containing products", null)));
    }

    @Override
    public ResponseEntity<GenericResponse<Product>> updateProduct(Long id, Product product) {
       
        Optional<Product> productFinded = productDao.findById(id);

        if (productFinded.isEmpty()){
            return ResponseEntity.status(404).body(new GenericResponse<>("404", "product not founded to delete", null));
        }

        product.setId(id);

        Product producUpdated = productDao.save(product);

        return ResponseEntity.ok(new GenericResponse<>("200", "product updated", producUpdated));
    }

    @Override
    public ResponseEntity<GenericResponse<Product>> deleteProduct(Long id) {
        
        Optional<Product> findProduct = productDao.findById(id);

        if( findProduct.isEmpty()){
            return ResponseEntity.status(404).body(new GenericResponse<>("404", "product not founed to delete", null));
        }

        findProduct.get().setStatus(ProductStatus.INACTIVE);

        productDao.save(findProduct.get());

        return ResponseEntity.ok(new GenericResponse<>("200", "product deleted", findProduct.get()));
    }
    
}
