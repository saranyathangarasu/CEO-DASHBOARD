package application.Dto;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class OperationalDataDto {

    private Long id;
    private String name;
    private Double value;
    
    
    private LocalDate date;


	public OperationalDataDto() {
		super();
	}


	public OperationalDataDto(Long id, String name, Double value, LocalDate date) {
		super();
		this.id = id;
		this.name = name;
		this.value = value;
		this.date = date;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Double getValue() {
		return value;
	}


	public void setValue(Double value) {
		this.value = value;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}
 
	
		
}