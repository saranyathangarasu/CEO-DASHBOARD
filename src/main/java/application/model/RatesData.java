package application.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rates_data")
public class RatesData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    
    private Integer customers;

    private int totalCustomers;

    private double acquisitionRate;

    public RatesData() {
    }

    public RatesData(LocalDate date, Integer customers, int totalCustomers, double acquisitionRate) {
        this.date = date;
        this.customers = customers;
        this.totalCustomers = totalCustomers;
        this.acquisitionRate = acquisitionRate;
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

    public Integer getCustomers() {
        return customers;
    }

    public void setCustomers(Integer customers) {
        this.customers = customers;
    }

    public int getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(int totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public double getAcquisitionRate() {
        return acquisitionRate;
    }

    public void setAcquisitionRate(double acquisitionRate) {
        this.acquisitionRate = acquisitionRate;
    }

    // Implement method to return acquisitions
    public int getAcquisitions() {
        return customers != null ? customers : 0;
    }

    // Implement method to return total visitors
    public int getTotalVisitors() {
        return totalCustomers;
    }
}
