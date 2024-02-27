package com.posapps.dtos;

import java.util.Optional;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CustomerAllDto {

   @NotNull(message = "offset harus diisi")
   private Integer offset;

   @NotNull(message = "pagessize harus diisi")
   private Integer pageSize;

   private Optional<String> field;
}
