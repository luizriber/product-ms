package br.com.luizr.santos.productms.service;

import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.luizr.santos.productms.controller.dto.ProductDto;
import br.com.luizr.santos.productms.controller.request.ProductRequest;
import br.com.luizr.santos.productms.model.Product;
import br.com.luizr.santos.productms.repository.ProductRepository;

@Service
public class ProductService {

	// Faz a injeção do DAO
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ModelMapper modelMapper;

	// Cadastra um novo produto
	@Transactional // Faz o commit na base da dados
	public ProductDto createProduct(ProductRequest productRequest) {
		Product product = modelMapper.map(productRequest, Product.class);
		productRepository.save(product);

		return modelMapper.map(product, ProductDto.class);
	}

	// Atualiza um produto
	@Transactional // Faz o commit na base da dados
	public ProductDto updateProduct(Long id, ProductRequest productRequest) {
		productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		Product product = modelMapper.map(productRequest, Product.class);
		product.setId(id);
		productRepository.save(product);

		return modelMapper.map(product, ProductDto.class);
	}

	// Consulta um produto pelo id
	public ProductDto findById(Long id) {
		Product productResul = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);

		return modelMapper.map(productResul, ProductDto.class);
	}

	// Retorna todos os produtos cadastrados
	public List<ProductDto> retrieveAllProducts() {
		return productRepository.findAll().stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
	}

	// Retorna os produtos de acordo com os parametros informados no search
	public List<ProductDto> searchProducts(String query, Double minimumPrice, Double maximumPrice) {
		List<Product> products = productRepository.findBySearch(query, minimumPrice, maximumPrice);
		return products.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
	}

	// Delete um produto
	@Transactional // Faz o commit na base da dados
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
}