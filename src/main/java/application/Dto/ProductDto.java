package application.Dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class ProductDto {

    private Long id;

    private String productName;
    private Double price;
    
	public ProductDto() {
		super();
	}

	public ProductDto(Long id, String productName, Double price) {
		super();
		this.id = id;
		this.productName = productName;
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	
}