package application.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "financeData")
public class FinanceData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Date date;
    private Double revenue;
    private Double expenses;
    private Double currentAssets;
    private Double currentLiabilities;
    

    public FinanceData() {
        super();
    }
    
    
    public FinanceData(Long id, Date date, Double revenue, Double expenses, Double currentAssets,
            Double currentLiabilities) {
        super();
        this.id = id;
        this.date = date;
        this.revenue = revenue;
        this.expenses = expenses;
        this.currentAssets = currentAssets;
        this.currentLiabilities = currentLiabilities;
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
    
    public Double getEquity() {
        // Assuming equity is calculated as the difference between total assets and total liabilities
        if (currentAssets != null && currentLiabilities != null) {
            return currentAssets - currentLiabilities;
        } else {
            return null; // Handle null values based on your business logic
        }
    }

    public Double getNetProfitMargin() {
        if (revenue != null && expenses != null && revenue != 0.0) {
            return ((revenue - expenses) / revenue) * 100.0;
        } else {
            return null;
        }
    }

    public Double getCurrentRatio() {
        if (currentLiabilities != null && currentLiabilities != 0.0) {
            return currentAssets / currentLiabilities;
        } else {
            return null;
        }
    }

    public Double getDebtToEquityRatio() {
        Double equity = getEquity();
        if (currentLiabilities != null && equity != null && equity != 0.0) {
            return currentLiabilities / equity;
        } else {
            return null;
        }
    }
}
