package application.Dto;

import java.util.Date;

public class FinancialRatioDto {

    private Date date;
    private Double netProfitMargin;
    private Double currentRatio;
    private Double debtToEquityRatio;
    
    
    public FinancialRatioDto() {
        super();
    }


    public FinancialRatioDto(Date date, Double netProfitMargin, Double currentRatio, Double debtToEquityRatio) {
        
        this.date = date;
        this.netProfitMargin = netProfitMargin;
        this.currentRatio = currentRatio;
        this.debtToEquityRatio = debtToEquityRatio;
    }


    public Date getDate() {
        return date;
    }


    public void setDate(Date date) {
        this.date = date;
    }


    public Double getNetProfitMargin() {
        return netProfitMargin;
    }


    public void setNetProfitMargin(Double netProfitMargin) {
        this.netProfitMargin = netProfitMargin;
    }


    public Double getCurrentRatio() {
        return currentRatio;
    }


    public void setCurrentRatio(Double currentRatio) {
        this.currentRatio = currentRatio;
    }


    public Double getDebtToEquityRatio() {
        return debtToEquityRatio;
    }


    public void setDebtToEquityRatio(Double debtToEquityRatio) {
        this.debtToEquityRatio = debtToEquityRatio;
    }

}
