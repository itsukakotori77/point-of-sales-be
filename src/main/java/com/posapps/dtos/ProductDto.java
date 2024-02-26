package com.posapps.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductDto {
   
   private Long id;

   @NotEmpty(message = "Nama harus diisi")
   private String name;

   private String image;

   @NotNull(message = "Harga harus diisi")
   private Double price;

   @NotNull(message = "Status harus diisi")
   private Boolean status;

   

}
