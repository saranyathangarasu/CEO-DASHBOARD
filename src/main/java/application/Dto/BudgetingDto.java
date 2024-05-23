package application.Dto;

import java.time.LocalDate;

public class BudgetingDto {
    
	private Long id;
	private LocalDate date;
    private String category;
    private Double plannedAmount;
    private Double actualAmount;
    private Double variance;
	public BudgetingDto() {
		super();
	}
	public BudgetingDto(Long id, LocalDate date, String category, Double plannedAmount, Double actualAmount,
			Double variance) {
		super();
		this.id = id;
		this.date = date;
		this.category = category;
		this.plannedAmount = plannedAmount;
		this.actualAmount = actualAmount;
		this.variance = variance;
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Double getPlannedAmount() {
		return plannedAmount;
	}
	public void setPlannedAmount(Double plannedAmount) {
		this.plannedAmount = plannedAmount;
	}
	public Double getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}
	public Double getVariance() {
	    if (actualAmount != null && plannedAmount != null) {
	        return actualAmount - plannedAmount;
	    } else {
	        return null; 
	    }
	}
	public void setVariance(Double variance) {
		this.variance = variance;
	}
	
    
}
