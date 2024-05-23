package application.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "conversionData")
public class ConversionData {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	private LocalDate date;
    private Integer noOfConversions;
    private Integer totalNumberOfVisitors;
    private Double conversionRate;
    
	public ConversionData() {
		super();
	}

	public ConversionData(Long id, LocalDate date, Integer noOfConversions, Integer totalNumberOfVisitors,
			Double conversionRate) {
		super();
		this.id = id;
		this.date = date;
		this.noOfConversions = noOfConversions;
		this.totalNumberOfVisitors = totalNumberOfVisitors;
		this.conversionRate = conversionRate;
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

	public Integer getNoOfConversions() {
		return noOfConversions;
	}

	public void setNoOfConversions(Integer noOfConversions) {
		this.noOfConversions = noOfConversions;
	}

	public Integer getTotalNumberOfVisitors() {
		return totalNumberOfVisitors;
	}

	public void setTotalNumberOfVisitors(Integer totalNumberOfVisitors) {
		this.totalNumberOfVisitors = totalNumberOfVisitors;
	}

	public Double getConversionRate() {
		return conversionRate;
	}

	public void setConversionRate(Double conversionRate) {
		this.conversionRate = conversionRate;
	}
    
    
}
