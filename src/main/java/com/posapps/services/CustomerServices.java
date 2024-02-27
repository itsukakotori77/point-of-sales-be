package com.posapps.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.posapps.entity.Customers;
import com.posapps.repositories.CustomerRepositories;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerServices {
   
   @Autowired
   private CustomerRepositories customer;

   public Customers save(Customers data) {
      return customer.save(data);
   }

   public Customers findOne(Long id){
      Optional<Customers> data = customer.findById(id);

      if(!data.isPresent()){
         return null;
      }

      return data.get();
   }

   public Iterable<Customers> findAll(){
      return customer.findAll();
   }

   public void removeOne(Long id){
      customer.deleteById(id);
   }

   public Page<Customers> findAllCustomer(Integer offset, Integer pageSize, Optional<String> field){
      Page<Customers> customerService = customer.findAll(
         !field.isPresent() 
            ? PageRequest.of(offset, pageSize)
            : PageRequest.of(offset, pageSize).withSort(Sort.by(field.get()))

      );

      return customerService;
   }
}
