package application.Dto;

import java.time.LocalDate;

import application.model.RatesData;

public class RatesDataDto {

	private Long id;
    private LocalDate date;
    private Integer customers;
    private int totalCustomers;
    private double acquisitionRate;

    public RatesDataDto() {
    }

    public RatesDataDto(RatesData ratesData) {
        this.id = ratesData.getId();
        this.date = ratesData.getDate();
        this.customers = ratesData.getCustomers();
        this.totalCustomers = ratesData.getTotalCustomers();
        this.acquisitionRate = ratesData.getAcquisitionRate();
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
}
