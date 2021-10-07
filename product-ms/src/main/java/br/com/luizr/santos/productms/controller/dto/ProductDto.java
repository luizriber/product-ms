package br.com.luizr.santos.productms.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

	private Long id;
	private String name;
	private String description;
	private Double price;
}