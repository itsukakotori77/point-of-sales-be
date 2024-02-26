package com.posapps.controllers;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;
import com.posapps.dtos.ProductDto;
import com.posapps.dtos.ProductsAllDto;
import com.posapps.entity.Products;
import com.posapps.repositories.ProductRepositories;
import com.posapps.services.ProductServices;
import com.posapps.utility.ErrorParsingUtility;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

   @Autowired
   private ModelMapper modelMapper;

   @Autowired
   private ProductServices productServices;

   @Autowired
   private ProductRepositories productRepositories;

   @Autowired
   private Faker faker;

   @PostMapping
   public ResponseEntity<?> create(
         @Valid @RequestBody ProductDto data,
         Errors errors) {
      Map<String, Object> map = new LinkedHashMap<String, Object>();

      if (errors.hasErrors()) {
         map.put("code", "01");
         map.put("message", "validasi error");
         map.put("data", ErrorParsingUtility.parse(errors));

         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
      }

      try {
         Products product = modelMapper.map(data, Products.class);
         map.put("code", "00");
         map.put("message", "data berhasil ditambahkan");
         map.put("data", productServices.save(product));

         return ResponseEntity.status(HttpStatus.OK).body(map);
      } catch (Exception error) {
         map.put("code", "99");
         map.put("message", "terjadi kesalahan pada proses inputan");

         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
      }
   }

   @GetMapping("/{id}")
   public ResponseEntity<?> findOne(@PathVariable("id") long id) {
      Map<String, Object> map = new LinkedHashMap<String, Object>();

      Products data = productServices.findOne(id);

      if (data == null) {
         map.put("code", "00");
         map.put("message", "data tidak ditemukan");
         return ResponseEntity.status(HttpStatus.OK).body(map);
      }

      map.put("code", "00");
      map.put("message", "data ditemukan");
      map.put("data", data);
      return ResponseEntity.status(HttpStatus.OK).body(map);
   }

   @GetMapping
   public ResponseEntity<?> all() {
      Map<String, Object> map = new LinkedHashMap<String, Object>();
      Iterable<Products> list = productServices.findAll();
      map.put("code", "00");
      map.put("message", "data berhasil ditampilkan");
      map.put("data", list);

      return ResponseEntity.status(HttpStatus.OK).body(map);
   }

   @GetMapping("/pagination")
   public ResponseEntity<?> allPagination(
         @Valid @RequestBody ProductsAllDto data,
         Errors errors) {
      Map<String, Object> map = new LinkedHashMap<String, Object>();

      if (data == null) {
         map.put("code", "00");
         map.put("message", "data tidak ditemukan");
         return ResponseEntity.status(HttpStatus.OK).body(map);
      }

      try {
         Page<Products> list = productServices.findProductWithPagination(
               data.getOffset(),
               data.getPageSize(),
               data.getField()
            );
         map.put("code", "00");
         map.put("message", "data berhasil ditampilkan");
         map.put("data", list);
   
         return ResponseEntity.status(HttpStatus.OK).body(map);
      } catch (Exception error) {
         map.put("code", "99");
         map.put("message", "terjadi kesalahan pada proses inputan");

         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
      }

   }

   @PutMapping
   public ResponseEntity<?> update(
         @Valid @RequestBody ProductDto data,
         Errors errors) {
      Map<String, Object> map = new LinkedHashMap<String, Object>();

      if (errors.hasErrors()) {
         map.put("code", "01");
         map.put("message", "validasi error");
         map.put("data", ErrorParsingUtility.parse(errors));

         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
      }

      try {
         Products product = modelMapper.map(data, Products.class);
         map.put("code", "00");
         map.put("message", "data berhasil ditambahkan");
         map.put("data", productServices.save(product));

         return ResponseEntity.status(HttpStatus.OK).body(map);
      } catch (Exception error) {
         map.put("code", "99");
         map.put("message", "terjadi kesalahan pada proses inputan");

         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
      }
   }

   @DeleteMapping()
   public ResponseEntity<?> deleteData(@RequestBody ProductDto productDto) {
      Map<String, Object> map = new LinkedHashMap<String, Object>();
      Products role = modelMapper.map(productDto, Products.class);

      try {
         productServices.removeOne(role.getId());
         map.put("code", "00");
         map.put("message", "data berhasil dihapus");

         return ResponseEntity.status(HttpStatus.OK).body(map);
      } catch (Exception ex) {
         map.put("code", "01");
         map.put("message", "internal server error");

         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
      }
   }

   @PostMapping("/inject_data")
   public ResponseEntity<?> initData() {
      Map<String, Object> map = new LinkedHashMap<String, Object>();
      
      try {
         // GENERATE DATA
         List<Products> product = IntStream.rangeClosed(1, 50)
            .mapToObj(i -> new Products(
               (long)i,
               faker.overwatch().hero(),
               "Description Products " + i,
               null,
               new Random().nextDouble(50000),
               new Random().nextBoolean(),
               new Date(),
               new Date()
            ))
            .collect(Collectors.toList());

         // SAVE DATA
         productRepositories.saveAll(product);

         map.put("code", "00");
         map.put("message", "data berhasil disimpan");

         return ResponseEntity.status(HttpStatus.OK).body(map);
      } catch (Exception ex) {
         map.put("code", "01");
         map.put("message", "internal server error");

         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
      }
   }
}
