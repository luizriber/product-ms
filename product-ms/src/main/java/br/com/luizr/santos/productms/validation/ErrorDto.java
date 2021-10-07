package br.com.luizr.santos.productms.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorDto {
	
	private int statusCode;
	private String message;
}