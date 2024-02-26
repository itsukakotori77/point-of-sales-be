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
@Table(name = "tbl_product")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Products implements Serializable{
   
   private static final Long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "name", length = 255)
   private String name;

   @Column(name = "desctiption", length = 255)
   private String description;

   @Column(name = "image", nullable = true)
   private String image;
   
   @Column(name = "price")
   private Double price;

   @Column(name = "status")
   private Boolean status;

   @CreationTimestamp
   private Date created_at;

   @UpdateTimestamp
   private Date updated_at;

}
