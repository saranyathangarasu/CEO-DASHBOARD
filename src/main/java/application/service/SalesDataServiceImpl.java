package application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.model.SalesData;
import application.repo.SalesDataRepository;
import jakarta.transaction.Transactional;

@Service
public class SalesDataServiceImpl implements SalesDataService {

    @Autowired
    private SalesDataRepository salesDataRepository;

    @Override
    public List<SalesData> getAllSalesData() {
        return salesDataRepository.findAll();
    }

    @Override
    public List<SalesData> getSalesDataByStatus(String status) {
        return salesDataRepository.findByStatus(status);
    }

    @Override
    public List<SalesData> getSalesDataByCompany(String company) {
        return salesDataRepository.findByCompany(company);
    }

    @Override
    public SalesData getSalesDataById(Long id) {
        return salesDataRepository.findById(id).orElse(null);
    }

    @Override
    public SalesData saveSalesData(SalesData salesData) {
        return salesDataRepository.save(salesData);
    }

    @Override
    public SalesData getLatestSalesData() {
        return salesDataRepository.findTopByOrderByTimestampDesc().orElse(null);
    }
    @Override
    @Transactional
    public void updateStatus(Long id, String status) {
        Optional<SalesData> optionalSalesData = salesDataRepository.findById(id);
        optionalSalesData.ifPresent(salesData -> {
            salesData.setStatus(status);
            salesDataRepository.save(salesData);
        });
    }
    @Override
    public void deleteSalesDataById(Long id) {
        salesDataRepository.deleteById(id);
    }
    
    @Override
    public List<String> getAllExistingData() {

        List<String> existingData = salesDataRepository.getAllExistingData(); 
        return existingData;
    }

}