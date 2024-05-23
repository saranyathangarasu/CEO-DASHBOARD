package application.service;

import java.util.List;

import application.model.SalesData;

public interface SalesDataService {
    List<SalesData> getAllSalesData();
    List<SalesData> getSalesDataByStatus(String status);
    List<SalesData> getSalesDataByCompany(String company);
    SalesData getSalesDataById(Long id);
    SalesData saveSalesData(SalesData salesData);
    SalesData getLatestSalesData();
	void updateStatus(Long id, String status);
	void deleteSalesDataById(Long id);
	List<String> getAllExistingData();
}