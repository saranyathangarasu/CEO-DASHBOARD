package application.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "figuresData")
public class FiguresData {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	private LocalDate date;
	private String productName;
	private Integer quantitySold;
	private Double totalRevenue;
	
	public FiguresData() {
		super();
	}


	public FiguresData(Long id, LocalDate date, String productName, Integer quantitySold, Double totalRevenue) {
		super();
		this.id = id;
		this.date = date;
		this.productName = productName;
		this.quantitySold = quantitySold;
		this.totalRevenue = totalRevenue;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public Integer getQuantitySold() {
		return quantitySold;
	}

	public void setQuantitySold(Integer quantitySold) {
		this.quantitySold = quantitySold;
	}

	public Double getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(Double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public Double calculateRevenue() {
        return quantitySold * totalRevenue;
    }
}