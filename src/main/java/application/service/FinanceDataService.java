package application.service;

import java.util.Date;
import java.util.List;

import application.Dto.FinanceDataDto;
import application.Dto.FinancialRatioDto;
import application.model.FinanceData;

public interface FinanceDataService {

    List<FinanceData> findAll();
    FinanceData findByFinanceDataDate(Date date);
    FinanceData save(FinanceDataDto financeDataDto);
    FinanceData getFinanceDataByDate(String date);
    List<FinancialRatioDto> calculateFinancialRatios(List<FinanceData> financeDataList);
    double calculateDebtToEquityRatio(Double totalLiabilities, Double equity);
    List<FinancialRatioDto> calculateNetProfitMarginData();
    List<FinanceData> getAllFinanceData();
    FinanceData findById(Long id);
    FinanceData update(FinanceDataDto financeDataDto);
    void deleteById(Long id);
    List<FinancialRatioDto> calculateDebtToEquityRatioData();
    List<FinancialRatioDto> calculateCurrentRatioData();
    FinanceData getFinanceData();

}
