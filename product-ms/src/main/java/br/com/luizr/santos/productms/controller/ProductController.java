package br.com.luizr.santos.productms.controller;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.luizr.santos.productms.controller.dto.ProductDto;
import br.com.luizr.santos.productms.controller.request.ProductRequest;
import br.com.luizr.santos.productms.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	// Método: POST - Cadastra um novo produto
	@PostMapping
	public ResponseEntity<ProductDto> create(@RequestBody @Valid ProductRequest productRequest, UriComponentsBuilder uriBuilder) {
		ProductDto productDto = productService.createProduct(productRequest);
		URI uri = uriBuilder.path("/products/{id}").buildAndExpand(productDto.getId()).toUri();

		return ResponseEntity.created(uri).body(productDto);
	}

	// Método: PUT - Atualiza um produto, de acordo com o id recebido como parametro
	@PutMapping("/{id}") // Faz o mapeamento de um parametro via PUT
	public ResponseEntity<ProductDto> update(@PathVariable Long id, @RequestBody @Valid ProductRequest productRequest) {
		return ResponseEntity.ok(productService.updateProduct(id, productRequest));
	}

	// Método: GET - Consulta um produto por id, de acordo com o id recebido como
	// parametro
	@GetMapping("/{id}") // Faz o mapeamento de um parametro via GET
	public ResponseEntity<ProductDto> retrieveById(@PathVariable Long id) {
		return ResponseEntity.ok(productService.findById(id));
	}

	// Método: GET - Lista todos os produtos cadastrados na base
	@GetMapping
	public List<ProductDto> retrieveAllProducts() {
		return productService.retrieveAllProducts();
	}

	// Método: GET - Lista os produtos de acordo com os parametros de filtros
	// informados
	@GetMapping("/search") // Faz o mapeamento de um parametro via GET
	public ResponseEntity<List<ProductDto>> search(@RequestParam(required = false) Double min_price, @RequestParam(required = false) Double max_price, @RequestParam(required = false) String q) {
		if(!(min_price == null && max_price == null && q == null)) {
			return ResponseEntity.ok(productService.searchProducts(q, min_price, max_price));
		}
		return ResponseEntity.badRequest().build();
	}

	// Método: DELETE - Faz a exclusao de um produto, de acordo com o parametro
	// recebido
	@DeleteMapping("/{id}") // Faz o mapeamento de um parametro via DELETE
	public ResponseEntity<?> delete(@PathVariable Long id) {
		productService.deleteProduct(id);
		return ResponseEntity.ok().build();
	}
}