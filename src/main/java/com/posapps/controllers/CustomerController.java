package com.posapps.controllers;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
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
import com.posapps.dtos.CustomerAllDto;
import com.posapps.dtos.CustomerDto;
import com.posapps.entity.Customers;
import com.posapps.repositories.CustomerRepositories;
import com.posapps.services.CustomerServices;
import com.posapps.utility.ErrorParsingUtility;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
   
   @Autowired
   private ModelMapper modelMapper;

   @Autowired
   private CustomerServices customerServices;

   @Autowired
   private Faker faker;

   @Autowired
   private CustomerRepositories customerRepositories;


   @PostMapping
   public ResponseEntity<?> create(
      @Valid @RequestBody CustomerDto data,
      Errors errors
   ) {
      Map<String, Object> map = new LinkedHashMap<String, Object>();

      if (errors.hasErrors()) {
         map.put("code", "01");
         map.put("message", "validasi error");
         map.put("data", ErrorParsingUtility.parse(errors));

         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
      }

      try {
         Customers product = modelMapper.map(data, Customers.class);
         map.put("code", "00");
         map.put("message", "data berhasil ditambahkan");
         map.put("data", customerServices.save(product));

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

      Customers data = customerServices.findOne(id);

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
   public ResponseEntity<?> all()
   {
      Map<String, Object> map = new LinkedHashMap<String, Object>();
      Iterable<Customers> list = customerServices.findAll();
      map.put("code", "00");
      map.put("message", "data berhasil ditampilkan");
      map.put("data", list);
      
      return ResponseEntity.status(HttpStatus.OK).body(map);
   }

   @GetMapping("/pagination")
   public ResponseEntity<?> allPagination(
         @Valid @RequestBody CustomerAllDto data,
         Errors errors) {
      Map<String, Object> map = new LinkedHashMap<String, Object>();

      if (data == null) {
         map.put("code", "00");
         map.put("message", "data tidak ditemukan");
         return ResponseEntity.status(HttpStatus.OK).body(map);
      }

      try {
         Page<Customers> list = customerServices.findAllCustomer(
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
      @Valid @RequestBody CustomerDto data,
      Errors errors
   ) {
      Map<String, Object> map = new LinkedHashMap<String, Object>();

      if (errors.hasErrors()) {
         map.put("code", "01");
         map.put("message", "validasi error");
         map.put("data", ErrorParsingUtility.parse(errors));

         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
      }

      try {
         Customers product = modelMapper.map(data, Customers.class);
         map.put("code", "00");
         map.put("message", "data berhasil ditambahkan");
         map.put("data", customerServices.save(product));

         return ResponseEntity.status(HttpStatus.OK).body(map);
      } catch (Exception error) {
         map.put("code", "99");
         map.put("message", "terjadi kesalahan pada proses inputan");

         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
      }
   }

   @DeleteMapping()
   public ResponseEntity<?> deleteData(@RequestBody CustomerDto customerDto)
   {
      Map<String, Object> map = new LinkedHashMap<String, Object>();
      Customers role = modelMapper.map(customerDto, Customers.class);

      try{
         customerServices.removeOne(role.getId());
         map.put("code", "00");
         map.put("message", "data berhasil dihapus");

         return ResponseEntity.status(HttpStatus.OK).body(map);
      }catch(Exception ex){
         map.put("code", "01");
         map.put("message", "internal server error");

         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
      }
   }

   @PostMapping("/initiate_data")
   public ResponseEntity<?> initData(){
      Map<String, Object> map = new LinkedHashMap<String, Object>();

      try{
         List<Customers> customer = IntStream.rangeClosed(1, 50)
            .mapToObj(i -> new Customers(
               (long)i,
               faker.name().firstName(),
               faker.name().lastName(),
               "test_email"+i+"@gmail.com",
               faker.address().streetAddress(),
               faker.phoneNumber().cellPhone(),
               new Date(), 
               new Date()  
            ))
            .collect(Collectors.toList());

            customerRepositories.saveAll(customer);

            map.put("code", "00");
            map.put("message", "data berhasil disimpan");
   
            return ResponseEntity.status(HttpStatus.OK).body(map);

      }catch(Exception Error){
         map.put("code", "01");
         map.put("message", "internal server error");

         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
      }
   }
}
