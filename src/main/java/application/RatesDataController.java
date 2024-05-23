package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import application.Dto.RatesDataDto;
import application.model.RatesData;
import application.service.RatesDataService;
import jakarta.validation.Valid;

@Controller
public class RatesDataController {

    private final RatesDataService ratesDataService;

    @Autowired
    public RatesDataController(RatesDataService ratesDataService) {
        this.ratesDataService = ratesDataService;
    }

    @GetMapping("/ratesDashboard")
    @PreAuthorize("hasAnyRole('CEO', 'Saleshead', 'Rateshead')")
    public String ratesDashboard(Model model) {
        List<RatesData> ratesDataList = ratesDataService.findAll();
        RatesData latestRatesData = ratesDataService.getLatestRatesData();
        List<LocalDate> dates = new ArrayList<>();
        List<Double> acquisitionRates = new ArrayList<>();
        for (RatesData ratesData : ratesDataList) {
            dates.add(ratesData.getDate());
            acquisitionRates.add(ratesData.getAcquisitionRate());
        }
        RatesDataDto ratesDataDto = new RatesDataDto();
        model.addAttribute("ratesDataList", ratesDataList);
        model.addAttribute("latestRatesData", latestRatesData);
        model.addAttribute("ratesDataDto", ratesDataDto);
        model.addAttribute("dates", dates);
        model.addAttribute("acquisitionRates", acquisitionRates);
        return "ratesDashboard";
    }

    @GetMapping("/showRatesUpdateForm")
    @PreAuthorize("hasAnyRole('CEO', 'Saleshead', 'Rateshead')")
    public String showRatesUpdateForm(Model model) {
        RatesDataDto ratesDataDto = new RatesDataDto(); 
        LocalDate previousDate = ratesDataService.getPreviousDate(); 
        model.addAttribute("ratesDataDto", ratesDataDto);
        model.addAttribute("previousDate", previousDate);
        return "ratesDashboard";
    }

    @PostMapping("/ratesUpdate")
    @PreAuthorize("hasAnyRole('CEO', 'Saleshead', 'Rateshead')")
    public String ratesUpdateSave(@Valid @ModelAttribute("ratesDataDto") RatesDataDto ratesDataDto,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Validation errors: " + bindingResult.getAllErrors());
            return "ratesDashboard";
        }
        ratesDataService.calculateAndUpdateRates(ratesDataDto);
        System.out.println("ratesUpdateSave method called");
        return "redirect:/ratesDashboard";
    }

    @GetMapping("/acquisitionRateData")
    @PreAuthorize("hasAnyRole('CEO', 'Saleshead', 'Rateshead')")
    public ResponseEntity<List<RatesData>> getAcquisitionRateData() {
        List<RatesData> acquisitionRateData = ratesDataService.getAcquisitionRateData();
        return ResponseEntity.ok(acquisitionRateData);
    }

    @GetMapping("/rates-logout")
    public String ratesLogout() {
        return "logout";
    }
    
    @GetMapping("/showRatesDashboard")
    @PreAuthorize("hasAnyRole('CEO', 'Saleshead', 'Rateshead')")
    public String showRatesDashboard(Model model) {
        List<RatesData> ratesDataList = ratesDataService.getAllRatesData();
        List<String> dates = new ArrayList<>();
        List<Double> acquisitionRates = new ArrayList<>();
        for (RatesData ratesData : ratesDataList) {
            LocalDate date = ratesData.getDate();
            Double acquisitionRate = ratesData.getAcquisitionRate();
            System.out.println("Date: " + date);
            System.out.println("Acquisition Rate: " + acquisitionRate);
            if (date != null) {
                dates.add(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            }
            if (acquisitionRate != null) {
                acquisitionRates.add(acquisitionRate);
            }
        }
        System.out.println("Dates: " + dates);
        System.out.println("Acquisition Rates: " + acquisitionRates);
        model.addAttribute("ratesDataList", ratesDataList);
        model.addAttribute("dates", dates);
        model.addAttribute("acquisitionRates", acquisitionRates);
        return "ratesDashboard";
    }
    
    @GetMapping("/ratesEdit")
    public String showRatesEditForm(@RequestParam("entryId") Long entryId, Model model) {
        RatesData ratesData = ratesDataService.findById(entryId);
        if (ratesData != null) {
            RatesDataDto ratesDataDto = new RatesDataDto(ratesData);
            model.addAttribute("ratesDataDto", ratesDataDto);
            return "ratesEdit";
        } else {
            return "redirect:/ratesDashboard"; 
        }
    }

    @PostMapping("/updateRatesEntry")
    @PreAuthorize("hasAnyRole('CEO', 'Saleshead', 'Rateshead')")
    public String updateRatesEntry(@Valid @ModelAttribute("ratesDataDto") RatesDataDto ratesDataDto,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "ratesEdit"; 
        }
        boolean updated = ratesDataService.updateRatesEntry(ratesDataDto);
        if (updated) {
            redirectAttributes.addFlashAttribute("successMessage", "Entry updated successfully");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Entry not found");
        }
        return "redirect:/ratesDashboard";
    }

    @DeleteMapping("/deleteRate/{id}")
    @PreAuthorize("hasAnyRole('CEO', 'Saleshead', 'Rateshead')")
    public ResponseEntity<String> deleteRateData(@PathVariable Long id) {
        ratesDataService.deleteById(id);
        return ResponseEntity.ok("Rate data with ID " + id + " deleted successfully");
    }

}
