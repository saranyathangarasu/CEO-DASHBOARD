package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

import application.model.FiguresData;
import application.service.FiguresDataService;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class RevenueController {
    @Autowired
    private FiguresDataService figuresDataService;
    @GetMapping("/api/revenue")
    public ResponseEntity<Double> getRevenueData(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        Double totalRevenue = figuresDataService.getTotalRevenueByDateRange(startDate, endDate);
        if (totalRevenue == null) {
            return ResponseEntity.ok(0.0); 
        }
        return ResponseEntity.ok(totalRevenue);
    }
    @GetMapping("/revenueDetails")
    public String revenueDetailsPage() {
        return "revenueDetails";
    }
    @GetMapping("/revenueChart")
    public String showRevenueChart(@RequestParam("startDate") LocalDate startDate,
                                   @RequestParam("endDate") LocalDate endDate,
                                   Model model) {
        List<FiguresData> revenueData = figuresDataService.getRevenueData(startDate, endDate);
        model.addAttribute("revenueData", revenueData);

        return "revenueChart"; 
    }
    @GetMapping("/api/revenue/download")
    public ResponseEntity<Resource> downloadExcelReport(
            @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            HttpServletResponse response) {
        figuresDataService.downloadRevenueReport(fromDate, toDate);
        String fileName = "revenue_report_" + LocalDate.now() + ".xlsx";
        String filePath = "F:/Saranya Jash/" + fileName; 
        Resource resource = new FileSystemResource(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}