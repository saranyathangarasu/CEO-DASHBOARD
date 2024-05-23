package application.service;

import java.util.List;
import java.time.LocalDate;
import application.Dto.RatesDataDto;
import application.model.RatesData;

public interface RatesDataService {

    List<RatesData> findAll();

    List<RatesData> getAcquisitionRateData();

    RatesData getLatestRatesData();

    void calculateAndUpdateRates(RatesDataDto ratesDataDto);

    LocalDate getPreviousDate();

    List<RatesData> getAllRatesData();

    RatesData findById(Long id);

    boolean updateRatesEntry(RatesDataDto ratesDataDto);

    RatesData convertToRatesData(RatesDataDto ratesDataDto);

    void updateRatesData(RatesDataDto ratesDataDto);

    void updateRatesData(RatesData ratesData);

    boolean deleteById(Long id); // Add this method declaration

    List<RatesData> getAcquisitionRateDataByDateRange(LocalDate startDate, LocalDate endDate);
}
