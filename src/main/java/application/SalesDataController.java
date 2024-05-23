package application;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import application.model.SalesData;
import application.service.SalesDataService;
import jakarta.validation.Valid;



@Controller
public class SalesDataController {

    @Autowired
    private SalesDataService salesDataService;

    @GetMapping("/salesDashboard")
    @PreAuthorize("hasAnyRole('CEO', 'Saleshead')")
    public String salesDashboard(Model model) {
        List<SalesData> salesDataList = salesDataService.getAllSalesData();
        List<String> statusOptions = Arrays.asList("new", "contacted", "lost");
        SalesData latestSalesData = salesDataService.getLatestSalesData();
        model.addAttribute("salesDataList", salesDataList);
        model.addAttribute("statusOptions", statusOptions);
        model.addAttribute("latestSalesData", latestSalesData);
        return "salesDashboard";
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<?> updateStatus(@RequestBody Map<String, Object> payload) {
        try {
            Long salesId = Long.valueOf(payload.get("id").toString());
            return ResponseEntity.ok("Status updated successfully");
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid Sales ID");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/salesUpdate")
    @PreAuthorize("hasAnyRole('CEO', 'Saleshead')")
    public String showSalesUpdatePage(Model model) {
        SalesData salesData = new SalesData();
        model.addAttribute("salesData", salesData);
        return "salesUpdate";
    }

    @PostMapping("/salesUpdate")
    @PreAuthorize("hasAnyRole('CEO', 'Saleshead')")
    public String salesUpdateSave(@ModelAttribute("salesData") @Valid SalesData salesData, BindingResult result) {
        if (result.hasErrors()) {
            return "salesUpdate";
        }
        salesDataService.saveSalesData(salesData);
        return "redirect:/salesDashboard";
    }

    @GetMapping("/salesEdit/{id}")
    @PreAuthorize("hasAnyRole('CEO', 'Saleshead')")
    public String showSalesEditPage(@PathVariable("id") Long id, Model model) {
        SalesData salesData = salesDataService.getSalesDataById(id);
        model.addAttribute("salesData", salesData);
        return "salesEdit"; 
    }

    @PostMapping("/updateSales")
    @PreAuthorize("hasAnyRole('CEO', 'Saleshead')")
    public String updateSalesData(@ModelAttribute("salesData") @Valid SalesData salesData, BindingResult result) {
        if (result.hasErrors()) {
            return "salesEdit"; 
        }
        salesDataService.saveSalesData(salesData); 
        return "redirect:/salesDashboard"; 
    }

    @PostMapping("/deleteSalesData")
    @PreAuthorize("hasAnyRole('CEO', 'Saleshead')")
    public String deleteSalesData(@RequestParam("id") Long id) {
        salesDataService.deleteSalesDataById(id);
        return "redirect:/salesDashboard"; 
    }

    @GetMapping("/fetchExistingDataEndpoint")
    public ResponseEntity<List<String>> fetchExistingData() {
        List<String> existingData = salesDataService.getAllExistingData(); 
        return ResponseEntity.ok(existingData);
    }

    @GetMapping("/sales/logout")
    public String logout() {
        return "logout";
    }
    @GetMapping("/financeOverview")
    public String financeOverviewPage() {
        return "financeOverview"; 
    }

}
