package com.onnivirtanen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@SpringBootApplication
@RestController
@RequestMapping("api/v1/products")
public class Main {

    private final ProductRepository productRepository;

    public Main(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping
    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    record NewProductRequest(
        String name,
        String description,
        Double price,
        Integer quantity,
        String shelfLocation,
        String category
    ) {

    }

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody NewProductRequest request) {
        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());
        product.setShelfLocation(request.shelfLocation());
        product.setCategory(request.category());
        productRepository.save(product);
        return ResponseEntity.ok("Product added successfully.");
    }

    @DeleteMapping("{productID}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productID") Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.deleteById(id);
            return ResponseEntity.ok("Product deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{productID}")
    public ResponseEntity<String> updateProduct(@PathVariable("productID") Integer id,
                                                @RequestBody NewProductRequest request) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(request.name());
            product.setDescription(request.description());
            product.setPrice(request.price());
            product.setQuantity(request.quantity());
            product.setShelfLocation(request.shelfLocation());
            product.setCategory(request.category());
            productRepository.save(product);
            return ResponseEntity.ok("Product updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
