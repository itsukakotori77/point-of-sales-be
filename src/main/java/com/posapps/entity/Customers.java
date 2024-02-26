package com.posapps.entity;

import java.io.Serializable;
import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "tbl_customer")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Customers implements Serializable{
   
   private static final Long serialVersionUID = 1L;
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "first_name", length = 50)
   private String first_name;

   @Column(name = "last_name", length = 50)
   private String last_name;

   @Column(name = "email", length = 50)
   private String email;

   @Column(name = "address", length = 255)
   private String address;

   @Column(name = "phone_number", length = 30)
   private String phone_number;

   @CreationTimestamp
   private Date created_at;

   @UpdateTimestamp
   private Date updated_at;
}
