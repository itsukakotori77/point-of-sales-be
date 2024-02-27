package com.posapps.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.posapps.entity.Products;
import com.posapps.repositories.ProductRepositories;
import java.util.Optional;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductServices {

   @Autowired
   private ProductRepositories product;

   public Products save(Products data) {
      return product.save(data);
   }

   public Products findOne(Long id) {
      Optional<Products> data = product.findById(id);

      if (!data.isPresent()) {
         return null;
      }

      return data.get();
   }

   public Iterable<Products> findAll() {
      return product.findAll();
   }

   public void removeOne(Long id) {
      product.deleteById(id);
   }

   public Page<Products> findProductWithPagination(Integer offset, Integer pageSize, Optional<String> field) {
      Page<Products> productService = product.findAll(
            !field.isPresent()
                  ? PageRequest.of(offset, pageSize)
                  : PageRequest.of(offset, pageSize).withSort(Sort.by(field.get()))
            );

      return productService;
   }

}
