package com.bootcamp.demo.bc_forum2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
  private String street;
  private String suite;
  private String city;
  private String zipcode;
  private GeoDTO geo;
}
