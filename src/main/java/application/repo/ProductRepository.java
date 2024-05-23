package application.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import application.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query("SELECT p.productName FROM Product p")
    List<String> getAllProductNames();
    @Query("SELECT p.price FROM Product p WHERE p.productName = :productName")
    Double findPriceByProductName(@Param("productName") String productName);
    @Query("SELECT COUNT(p) FROM Product p WHERE p.productName = :productName")
    long countByProductName(@Param("productName") String productName);
}