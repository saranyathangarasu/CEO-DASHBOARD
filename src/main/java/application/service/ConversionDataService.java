package application.service;

import java.time.LocalDate;
import java.util.List;

import application.model.ConversionData;

public interface ConversionDataService {

    List<ConversionData> getAllConversionData();

    ConversionData getConversionDataById(Long id);

    List<ConversionData> getConversionDataByDateRange(LocalDate startDate, LocalDate endDate);

    ConversionData saveConversionData(ConversionData conversionData);

    void deleteConversionData(Long id);
    
    void deleteConversionDataById(Long id);
    
}