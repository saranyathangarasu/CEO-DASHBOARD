package application.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.Dto.FiguresDataDto;
import application.model.FiguresData;
import application.repo.FiguresDataRepository;
import application.repo.ProductRepository;
import jakarta.validation.Valid;

@Service
public class FiguresDataServiceImpl implements FiguresDataService {

	private final FiguresDataRepository figuresDataRepository;
    private final ProductRepository productRepository;

    @Autowired
    public FiguresDataServiceImpl(FiguresDataRepository figuresDataRepository, ProductRepository productRepository) {
        this.figuresDataRepository = figuresDataRepository;
        this.productRepository = productRepository;
    }
    @Override
    public List<Object[]> getTotalRevenueByDate() {
        return figuresDataRepository.getTotalRevenueByDate();
    }

    @Override
    public List<FiguresData> getAllFiguresData() {
        return figuresDataRepository.findAll();
    }

    @Override
    public FiguresData getFiguresDataById(Long id) {
        return figuresDataRepository.findById(id).orElse(null);
    }

    @Override
    public void saveFiguresData(FiguresData figuresData) {
        figuresDataRepository.save(figuresData);
    }
    @Override
    public double getProductPrice(String productName) {
        Double price = productRepository.findPriceByProductName(productName);
        if (price != null) {
            return price; 
        } else {
            return 0.0; 
        }
    }
    
    @Override
    public void save(@Valid FiguresDataDto figuresDataDto) {
        double productPrice = getProductPrice(figuresDataDto.getProductName());
        double totalRevenue = figuresDataDto.getQuantitySold() * productPrice;
        figuresDataDto.setTotalRevenue(totalRevenue);
        FiguresData figuresData = convertDtoToEntity(figuresDataDto);
        figuresDataRepository.save(figuresData);
    }
    
    @Override
    public void deleteFigureById(Long id) {
        figuresDataRepository.deleteById(id);
    }

    private FiguresData convertDtoToEntity(FiguresDataDto figuresDataDto) {
        FiguresData figuresData = new FiguresData();
        figuresData.setProductName(figuresDataDto.getProductName());
        figuresData.setQuantitySold(figuresDataDto.getQuantitySold());
        figuresData.setTotalRevenue(figuresDataDto.getTotalRevenue());
        figuresData.setDate(figuresDataDto.getDate());
        return figuresData;
    }

    @Override
    public double getTodayRevenue(LocalDate date) {
        List<FiguresData> revenueData = figuresDataRepository.getRevenueForDate(date);
        double totalRevenue = 0.0;
        for (FiguresData data : revenueData) {
            totalRevenue += data.getTotalRevenue();
        }
        return totalRevenue;
    }
    @Override
    public List<FiguresData> getRevenueByDateRange(LocalDate fromDate, LocalDate toDate) {
        return figuresDataRepository.findByDateBetween(fromDate, toDate);
    }

    @Override
    public List<FiguresData> getRevenueData(LocalDate startDate, LocalDate endDate) {
        return figuresDataRepository.findByDateBetween(startDate, endDate);
    }
    
    @Override
    public List<FiguresData> getRevenueForDate(LocalDate date) {
        List<FiguresData> revenueData = figuresDataRepository.getRevenueForDate(date);
        if (revenueData.isEmpty()) {
            throw new RuntimeException("No revenue data available for the given date.");
        }
        return revenueData;
    }

    @Override
    public List<FiguresData> getRevenueBetweenDates(LocalDate startDate, LocalDate endDate) {
        List<FiguresData> revenueData = figuresDataRepository.findByDateBetween(startDate, endDate);
        if (revenueData.isEmpty()) {
            throw new RuntimeException("No revenue data available between the given dates.");
        }
        return revenueData;
    }
    @Override
    public Double getTotalRevenueByDateRange(LocalDate fromDate, LocalDate toDate) {
        List<FiguresData> figuresDataList = figuresDataRepository.findByDateBetween(fromDate, toDate);
        Double totalRevenue = figuresDataList.stream()
                .mapToDouble(FiguresData::getTotalRevenue)
                .sum();
        return totalRevenue;
    }

    @Override
    public List<Object[]> getRevenueChartData(LocalDate startDate, LocalDate endDate) {
        return figuresDataRepository.getRevenueChartData(startDate, endDate);
    }

    @Override
    public void downloadRevenueReport(LocalDate fromDate, LocalDate toDate) {
        List<FiguresData> revenueData = getRevenueData(fromDate, toDate);

        try (Workbook workbook = new XSSFWorkbook()) { 
            Sheet sheet = workbook.createSheet("Revenue Report");
            createHeaderRow(sheet);

            int rowNum = 1;
            for (FiguresData data : revenueData) {
                Row row = sheet.createRow(rowNum++);
                createDataRow(row, data);
            }

            String fileName = "revenue_report_" + LocalDate.now() + ".xlsx";
            String filePath = "F:/Saranya Jash/" + fileName; 

            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }

            String downloadLink = "/api/revenue/download?fileName=" + fileName;
            System.out.println("Excel file generated and saved: " + filePath);
            System.out.println("Download link: " + downloadLink);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void createHeaderRow(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Date", "Product Name", "Quantity Sold", "Total Revenue"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            CellStyle style = sheet.getWorkbook().createCellStyle();
            Font font = sheet.getWorkbook().createFont();
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            style.setFont(font);
            style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
        }
    }

    private void createDataRow(Row row, FiguresData data) {
        row.createCell(0).setCellValue(data.getDate().toString());
        row.createCell(1).setCellValue(data.getProductName());
        row.createCell(2).setCellValue(data.getQuantitySold());
        row.createCell(3).setCellValue(data.getTotalRevenue());
    }

}
