package application.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.model.ConversionData;
import application.repo.ConversionDataRepository;

@Service
public class ConversionDataServiceImpl implements ConversionDataService {

    private final ConversionDataRepository conversionDataRepository;

    @Autowired
    public ConversionDataServiceImpl(ConversionDataRepository conversionDataRepository) {
        this.conversionDataRepository = conversionDataRepository;
    }

    @Override
    public List<ConversionData> getAllConversionData() {
        return conversionDataRepository.findAll();
    }

    @Override
    public ConversionData getConversionDataById(Long id) {
        return conversionDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ConversionData not found with id: " + id));
    }

    @Override
    public List<ConversionData> getConversionDataByDateRange(LocalDate startDate, LocalDate endDate) {
        return conversionDataRepository.findByDateBetween(startDate, endDate);
    }

    @Override
    public ConversionData saveConversionData(ConversionData conversionData) {
        return conversionDataRepository.save(conversionData);
    }

    @Override
    public void deleteConversionData(Long id) {
        conversionDataRepository.deleteById(id);
    }
    
    @Override
    public void deleteConversionDataById(Long id) {
        conversionDataRepository.deleteById(id);
    }
}