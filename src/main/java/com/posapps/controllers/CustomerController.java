package com.posapps.controllers;

import java.util.LinkedHashMap;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.posapps.dtos.CustomerDto;
import com.posapps.entity.Customers;
import com.posapps.services.CustomerServices;
import com.posapps.utility.ErrorParsingUtility;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
   
   @Autowired
   private ModelMapper modelMapper;

   @Autowired
   private CustomerServices customerServices;

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
}
