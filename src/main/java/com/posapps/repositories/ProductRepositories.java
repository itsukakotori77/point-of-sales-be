package com.posapps.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.posapps.entity.Products;

public interface ProductRepositories extends JpaRepository<Products, Long> {
   // List<Products> findAllByPrice(double price, Pageable pageable);
}
