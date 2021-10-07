package br.com.luizr.santos.productms.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import br.com.luizr.santos.productms.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	// Query que faz o Search através do campos recebidos como parametros
	@Query("SELECT p FROM Product p WHERE (:q is null or UPPER(p.name) LIKE '%' || UPPER(:q) || '%' or UPPER(p.description) LIKE '%' || UPPER(:q) || '%') and (:min_price is null or p.price>=:min_price) and (:max_price is null or p.price<=:max_price)")
	List<Product> findBySearch (String q, Double min_price, Double max_price);
	
}