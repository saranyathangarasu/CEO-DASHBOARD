package application.Dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import application.model.FinanceData;

public class FinanceDataDto {

    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private Double revenue;
    private Double expenses;
    private Double currentAssets;
    private Double currentLiabilities;

    public FinanceDataDto() {
        super();
    }

    public FinanceDataDto(Long id, Date date, Double revenue, Double expenses, Double currentAssets, Double currentLiabilities) {
        this.id = id;
        this.date = date;
        this.revenue = revenue;
        this.expenses = expenses;
        this.currentAssets = currentAssets;
        this.currentLiabilities = currentLiabilities;
    }


    public FinanceDataDto(FinanceData financeData) {
        this.id = financeData.getId();
        this.date = financeData.getDate();
        this.revenue = financeData.getRevenue();
        this.expenses = financeData.getExpenses();
        this.currentAssets = financeData.getCurrentAssets();
        this.currentLiabilities = financeData.getCurrentLiabilities();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public Double getExpenses() {
        return expenses;
    }

    public void setExpenses(Double expenses) {
        this.expenses = expenses;
    }

    public Double getCurrentAssets() {
        return currentAssets;
    }

    public void setCurrentAssets(Double currentAssets) {
        this.currentAssets = currentAssets;
    }

    public Double getCurrentLiabilities() {
        return currentLiabilities;
    }

    public void setCurrentLiabilities(Double currentLiabilities) {
        this.currentLiabilities = currentLiabilities;
    }
    
    
}
