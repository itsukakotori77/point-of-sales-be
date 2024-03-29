package com.posapps.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductsAllDto {

   @NotNull(message = "offset harus diisi")
   private Integer offset;
   
   @NotNull(message = "pagesize harus diisi")
   private Integer pageSize;
   
   private Optional<String> field;
}
