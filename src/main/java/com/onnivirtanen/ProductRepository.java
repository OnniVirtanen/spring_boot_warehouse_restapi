package com.onnivirtanen;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository
        extends JpaRepository <Product, Integer> {
}
