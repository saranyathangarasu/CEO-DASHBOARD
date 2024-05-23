package application.service;

import application.Dto.RatesDataDto;
import application.model.RatesData;
import application.repo.RatesDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RatesDataServiceImpl implements RatesDataService {

    private final RatesDataRepository ratesDataRepository;

    @Autowired
    public RatesDataServiceImpl(RatesDataRepository ratesDataRepository) {
        this.ratesDataRepository = ratesDataRepository;
    }

    @Override
    public List<RatesData> findAll() {
        return ratesDataRepository.findAll();
    }

    @Override
    public List<RatesData> getAcquisitionRateData() {
        return ratesDataRepository.findAll();
    }

    @Override
    public RatesData getLatestRatesData() {
        List<RatesData> latestRatesDataList = ratesDataRepository.findAllByOrderByDateDesc();
        return latestRatesDataList.isEmpty() ? null : latestRatesDataList.get(0);
    }

    @Override
    public void calculateAndUpdateRates(RatesDataDto ratesDataDto) {
        RatesData ratesData = new RatesData();
        ratesData.setDate(ratesDataDto.getDate());
        ratesData.setCustomers(ratesDataDto.getCustomers());
        RatesData latestRatesData = getLatestRatesData();
        if (latestRatesData != null) {
            int newCustomers = ratesData.getCustomers() != null ? ratesData.getCustomers() : 0;
            int previousTotalCustomers = latestRatesData.getTotalCustomers();
            int totalCustomers = previousTotalCustomers + newCustomers;
            ratesData.setTotalCustomers(totalCustomers);
            if (previousTotalCustomers != 0) {
                double acquisitionRate = ((double) newCustomers / totalCustomers) * 100; 
                ratesData.setAcquisitionRate(acquisitionRate);
            } else {
                ratesData.setAcquisitionRate(0); 
            }
        } else {
            ratesData.setTotalCustomers(ratesData.getCustomers() != null ? ratesData.getCustomers() : 0);
            ratesData.setAcquisitionRate(0);
        }
        ratesDataRepository.save(ratesData);
    }

    @Override
    public LocalDate getPreviousDate() {
        return ratesDataRepository.findPreviousDate(LocalDate.now());
    }

    @Override
    public List<RatesData> getAllRatesData() {
        return ratesDataRepository.findAll();
    }

    @Override
    public RatesData findById(Long id) {
        Optional<RatesData> optionalRatesData = ratesDataRepository.findById(id);
        return optionalRatesData.orElse(null);
    }

    @Override
    @Transactional
    public boolean updateRatesEntry(RatesDataDto ratesDataDto) {
        try {
            Optional<RatesData> optionalRatesData = ratesDataRepository.findById(ratesDataDto.getId());
            if (optionalRatesData.isPresent()) {
                RatesData existingRatesData = optionalRatesData.get();
                existingRatesData.setDate(ratesDataDto.getDate());
                existingRatesData.setCustomers(ratesDataDto.getCustomers());
                ratesDataRepository.save(existingRatesData);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    @Transactional
    public void updateRatesData(RatesDataDto ratesDataDto) {
        RatesData ratesData = convertToRatesData(ratesDataDto);
        ratesDataRepository.save(ratesData);
    }

    @Override
    public void updateRatesData(RatesData ratesData) {
        ratesDataRepository.save(ratesData);
    }

    @Override
    public RatesData convertToRatesData(RatesDataDto ratesDataDto) {
        RatesData ratesData = new RatesData();
        ratesData.setId(ratesDataDto.getId());
        ratesData.setDate(ratesDataDto.getDate());
        ratesData.setCustomers(ratesDataDto.getCustomers());
        return ratesData;
    }
    
    @Override
    public boolean deleteById(Long id) {
        Optional<RatesData> optionalRatesData = ratesDataRepository.findById(id);
        if (optionalRatesData.isPresent()) {
            ratesDataRepository.deleteById(id);
            return true;
        } else {
            return false; // Entry not found
        }
    }
    public void deleteRateDataById(Long id) {
        ratesDataRepository.deleteById(id);
    }
    @Override
    public List<RatesData> getAcquisitionRateDataByDateRange(LocalDate startDate, LocalDate endDate) {
        return ratesDataRepository.findByDateBetween(startDate, endDate);
    }
}
