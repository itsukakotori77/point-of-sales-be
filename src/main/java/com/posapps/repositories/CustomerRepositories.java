package com.posapps.repositories;

import com.posapps.entity.Customers;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepositories extends JpaRepository<Customers, Long>{
   
}
