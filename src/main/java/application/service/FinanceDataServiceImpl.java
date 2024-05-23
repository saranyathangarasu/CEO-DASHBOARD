package application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import application.Dto.FinanceDataDto;
import application.Dto.FinancialRatioDto;
import application.model.FinanceData;
import application.repo.FinanceDataRepository;

@Service
public class FinanceDataServiceImpl implements FinanceDataService {

    private final FinanceDataRepository financeDataRepository;

    @Autowired
    public FinanceDataServiceImpl(FinanceDataRepository financeDataRepository) {
        this.financeDataRepository = financeDataRepository;
    }

    @Override
    public List<FinanceData> findAll() {
        return financeDataRepository.findAll();
    }
    
    @Override
    public FinanceData findById(Long id) {
        return financeDataRepository.findById(id).orElse(null);
    }


    @Override
    public FinanceData findByFinanceDataDate(Date date) {
        return financeDataRepository.findByDate(date);
    }

    @Override
    public FinanceData save(FinanceDataDto financeDataDto) {
        FinanceData financeData = new FinanceData();
        financeData.setDate(financeDataDto.getDate());
        financeData.setRevenue(financeDataDto.getRevenue());
        financeData.setExpenses(financeDataDto.getExpenses());
        financeData.setCurrentAssets(financeDataDto.getCurrentAssets());
        financeData.setCurrentLiabilities(financeDataDto.getCurrentLiabilities());
        return financeDataRepository.save(financeData);
    }

    @Override
    public FinanceData getFinanceDataByDate(String date) {
        Date convertedDate = parseDateStringToDate(date);
        return financeDataRepository.findByDate(convertedDate);
    }

    @Override
    public List<FinancialRatioDto> calculateFinancialRatios(List<FinanceData> financeDataList) {
        List<FinancialRatioDto> financialRatios = new ArrayList<>();

        for (FinanceData financeData : financeDataList) {
            FinancialRatioDto ratioDto = new FinancialRatioDto();
            ratioDto.setDate(financeData.getDate());

            Double netProfitMargin = calculateNetProfitMargin(financeData.getRevenue(), financeData.getExpenses());
            ratioDto.setNetProfitMargin(netProfitMargin);

            Double currentRatio = calculateCurrentRatio(financeData.getCurrentAssets(), financeData.getCurrentLiabilities());
            ratioDto.setCurrentRatio(currentRatio);

            Double debtToEquityRatio = calculateDebtToEquityRatio(financeData.getCurrentLiabilities(), financeData.getEquity());
            ratioDto.setDebtToEquityRatio(debtToEquityRatio);

            financialRatios.add(ratioDto);
        }

        return financialRatios;
    }

    @Override
    public double calculateDebtToEquityRatio(Double totalLiabilities, Double equity) {
        if (equity != null && equity != 0.0) {
            return totalLiabilities / equity;
        } else {
            return 0.0;
        }
    }

    @Override
    public List<FinancialRatioDto> calculateNetProfitMarginData() {
        List<FinanceData> financeDataList = financeDataRepository.findAll();
        return calculateFinancialRatios(financeDataList);
    }

    @Override
    public List<FinanceData> getAllFinanceData() {
        return financeDataRepository.findAll();
    }

    private Date parseDateStringToDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Double calculateNetProfitMargin(Double revenue, Double expenses) {
        if (revenue != null && expenses != null && revenue != 0.0) {
            return ((revenue - expenses) / revenue) * 100.0;
        } else {
            return null;
        }
    }

    @Override
    public FinanceData update(FinanceDataDto financeDataDto) {
        if (financeDataDto.getId() == null) {
            return null;
        }

        FinanceData financeData = financeDataRepository.findById(financeDataDto.getId()).orElse(null);

        if (financeData != null) {
            financeData.setDate(financeDataDto.getDate());
            financeData.setRevenue(financeDataDto.getRevenue());
            financeData.setExpenses(financeDataDto.getExpenses());
            financeData.setCurrentAssets(financeDataDto.getCurrentAssets());
            financeData.setCurrentLiabilities(financeDataDto.getCurrentLiabilities());

            return financeDataRepository.save(financeData);
        } else {
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        financeDataRepository.deleteById(id);
    }

    @Override
    public List<FinancialRatioDto> calculateDebtToEquityRatioData() {
        List<FinanceData> financeDataList = financeDataRepository.findAll();
        List<FinancialRatioDto> financialRatios = new ArrayList<>();

        for (FinanceData financeData : financeDataList) {
            FinancialRatioDto ratioDto = new FinancialRatioDto();
            ratioDto.setDate(financeData.getDate());

            Double debtToEquityRatio = calculateDebtToEquityRatio(financeData.getCurrentLiabilities(), financeData.getEquity());
            ratioDto.setDebtToEquityRatio(debtToEquityRatio);

            financialRatios.add(ratioDto);
        }

        return financialRatios;
    }

    @Override
    public List<FinancialRatioDto> calculateCurrentRatioData() {
        List<FinanceData> financeDataList = financeDataRepository.findAll();
        List<FinancialRatioDto> financialRatios = new ArrayList<>();

        for (FinanceData financeData : financeDataList) {
            FinancialRatioDto ratioDto = new FinancialRatioDto();
            ratioDto.setDate(financeData.getDate());

            Double currentRatio = calculateCurrentRatio(financeData.getCurrentAssets(), financeData.getCurrentLiabilities());
            ratioDto.setCurrentRatio(currentRatio);

            financialRatios.add(ratioDto);
        }

        return financialRatios;
    }

    private Double calculateCurrentRatio(Double currentAssets, Double currentLiabilities) {
        if (currentLiabilities != null && currentLiabilities != 0.0) {
            return currentAssets / currentLiabilities;
        } else {
            return null;
        }
    }

    @Override
    public FinanceData getFinanceData() {
        Date today = new Date();
        FinanceData financeData = financeDataRepository.findByFinanceDataDate(today);
        if (financeData == null) {
            return null;
        }
        return financeData;
    }

    
}
