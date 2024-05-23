package application.service;

import java.time.LocalDate;
import java.util.List;

import application.Dto.FiguresDataDto;
import application.model.FiguresData;
import jakarta.validation.Valid;

public interface FiguresDataService {

    List<FiguresData> getAllFiguresData();
    FiguresData getFiguresDataById(Long id);
    void saveFiguresData(FiguresData figuresData);
    void save(@Valid FiguresDataDto figuresDataDto);   
    List<Object[]> getTotalRevenueByDate();
    double getProductPrice(String productName);
    void deleteFigureById(Long id);
    double getTodayRevenue(LocalDate date);
    List<FiguresData> getRevenueByDateRange(LocalDate fromDate, LocalDate toDate);
    List<FiguresData> getRevenueData(LocalDate startDate, LocalDate endDate);
    List<FiguresData> getRevenueForDate(LocalDate date);
    List<FiguresData> getRevenueBetweenDates(LocalDate startDate, LocalDate endDate);
    Double getTotalRevenueByDateRange(LocalDate startDate, LocalDate endDate);
    List<Object[]> getRevenueChartData(LocalDate startDate, LocalDate endDate);
    void downloadRevenueReport(LocalDate fromDate, LocalDate toDate);
}