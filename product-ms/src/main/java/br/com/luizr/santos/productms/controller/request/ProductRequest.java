package br.com.luizr.santos.productms.controller.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;
import lombok.Getter;

@Getter
public class ProductRequest {

	@NotNull(message = "O campo name não pode ser nulo")
	@NotEmpty(message = "O campo name não pode ser vazio")
	private String name;
	@NotNull(message = "O campo description não pode ser nulo")
	@NotEmpty(message = "O campo description não pode ser vazio")
	@Length(min = 10, message = "O campo description deve ter 10 caracteres no mínimo")
	private String description;
	@Positive(message = "O campo price não pode ser negativo")
	private Double price;
}