package application;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import application.model.ConversionData;
import application.model.Product;
import application.model.RatesData;
import application.service.ConversionDataService;
import application.service.FiguresDataService;
import application.service.ProductService;
import application.service.RatesDataService;

@Controller
public class SalesOverviewController {

	private final FiguresDataService figuresDataService;
	
	private final ConversionDataService conversionDataService;
	
	private final RatesDataService ratesDataService;
	
	private final ProductService productService;

	@Autowired
	public SalesOverviewController(FiguresDataService figuresDataService, ConversionDataService conversionDataService, RatesDataService ratesDataService, ProductService productService) {
	    this.figuresDataService = figuresDataService;
	    this.conversionDataService = conversionDataService;
	    this.ratesDataService = ratesDataService;
	    this.productService = productService;
	}
    
	@GetMapping("/salesOverview")
    public String salesOverviewPage() {
        return "salesOverview"; 
    }
	
	@GetMapping("/api/todayRevenue")
    public ResponseEntity<Double> getTodayRevenue(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Double todayRevenue = figuresDataService.getTodayRevenue(date);
        return ResponseEntity.ok(todayRevenue);
    }
	
	@GetMapping("/api/currentMonthConversionRate")
    public ResponseEntity<Double> getCurrentMonthConversionRate() {
        YearMonth currentMonth = YearMonth.now();
        LocalDate startDate = currentMonth.atDay(1);
        LocalDate endDate = currentMonth.atEndOfMonth();
        List<ConversionData> conversionData = conversionDataService.getConversionDataByDateRange(startDate, endDate);
        int totalConversions = conversionData.stream().mapToInt(ConversionData::getNoOfConversions).sum();
        int totalVisitors = conversionData.stream().mapToInt(ConversionData::getTotalNumberOfVisitors).sum();
        double conversionRate = totalVisitors > 0 ? (double) totalConversions / totalVisitors * 100.0 : 0.0;
        return ResponseEntity.ok(conversionRate);
    }
	
	@GetMapping("/api/currentMonthAcquisitionRate")
    public ResponseEntity<Double> getCurrentMonthAcquisitionRate() {
        YearMonth currentMonth = YearMonth.now();
        LocalDate startDate = currentMonth.atDay(1);
        LocalDate endDate = currentMonth.atEndOfMonth();
        List<RatesData> acquisitionRateData = ratesDataService.getAcquisitionRateDataByDateRange(startDate, endDate);
        int totalAcquisitions = acquisitionRateData.stream().mapToInt(RatesData::getAcquisitions).sum();
        int totalVisitors = acquisitionRateData.stream().mapToInt(RatesData::getTotalVisitors).sum();
        double acquisitionRate = totalVisitors > 0 ? (double) totalAcquisitions / totalVisitors * 100.0 : 0.0;
        return ResponseEntity.ok(acquisitionRate);
    }
	
	@GetMapping("/api/currentProducts")
    public ResponseEntity<List<Product>> getCurrentProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}
