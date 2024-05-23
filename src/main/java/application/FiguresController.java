
package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import application.Dto.FiguresDataDto;

import application.model.FiguresData;
import application.service.FiguresDataService;
import jakarta.validation.Valid;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FiguresController {

    private final FiguresDataService figuresDataService;

    @Autowired
    public FiguresController(FiguresDataService figuresDataService) {
        this.figuresDataService = figuresDataService;
    }

    @GetMapping("/figures")
    @PreAuthorize("hasAnyRole('HO_Company', 'Saleshead', 'Figureshead')")
    public String showFigures(Model model) {
        List<FiguresData> figuresDataList = figuresDataService.getAllFiguresData();
        model.addAttribute("figuresDataList", figuresDataList);
        List<Object[]> revenueData = figuresDataService.getTotalRevenueByDate();
        model.addAttribute("revenueData", revenueData);
        return "figures";
    }

    @GetMapping("/api/figures")
    @PreAuthorize("hasAnyRole('HO_Company', 'Saleshead', 'Figureshead')")
    public ResponseEntity<?> getFiguresData() {
        try {
            List<Object[]> revenueData = figuresDataService.getTotalRevenueByDate();
            return ResponseEntity.ok().body(Map.of("revenueData", revenueData));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/figuresUpdate")
    @PreAuthorize("hasAnyRole('HO_Company', 'Saleshead', 'Figureshead')")
    public String updateFigures(@ModelAttribute FiguresData figuresData, Model model) {
        model.addAttribute("figuresData", figuresData);
        figuresDataService.saveFiguresData(figuresData);
        return "redirect:/figures";
    }
    @GetMapping("/figuresUpdate")
    @PreAuthorize("hasAnyRole('HO_Company', 'Saleshead', 'Figureshead')")
    public String showFiguresUpdateForm(Model model) {
        FiguresDataDto figuresDataDto = new FiguresDataDto(); 
        model.addAttribute("figuresDataDto", figuresDataDto); 
        return "figuresUpdate";
    }

    @PostMapping("/figuresUpdateSave")
    @PreAuthorize("hasAnyRole('HO_Company', 'Saleshead', 'Figureshead')")
    public String figuresUpdateSave(@ModelAttribute("figuresDataDto") @Valid FiguresDataDto figuresDataDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "figuresUpdate";
        }
        double productPrice = figuresDataService.getProductPrice(figuresDataDto.getProductName());
        double totalRevenue = productPrice * figuresDataDto.getQuantitySold();
        FiguresData figuresData = new FiguresData();
        figuresData.setDate(figuresDataDto.getDate());
        figuresData.setProductName(figuresDataDto.getProductName());
        figuresData.setQuantitySold(figuresDataDto.getQuantitySold());
        figuresData.setTotalRevenue(totalRevenue);
        figuresDataService.saveFiguresData(figuresData);
        return "redirect:/figures";
    }

    @GetMapping("/figuresEdit/{id}") 
    @PreAuthorize("hasAnyRole('HO_Company', 'Saleshead', 'Figureshead')")
    public String editFigures(@PathVariable Long id, Model model) {
        FiguresData figuresData = figuresDataService.getFiguresDataById(id);
        if (figuresData != null) {
            FiguresDataDto figuresDataDto = new FiguresDataDto();
            figuresDataDto.setId(figuresData.getId());
            figuresDataDto.setProductName(figuresData.getProductName());
            figuresDataDto.setQuantitySold(figuresData.getQuantitySold());
            figuresDataDto.setDate(figuresData.getDate());
            model.addAttribute("figuresDataDto", figuresDataDto);
            return "figuresEdit"; 
        } else {
            return "redirect:/figures"; 
        }
    }

    @PostMapping("/figures/updateSave") 
    @PreAuthorize("hasAnyRole('HO_Company', 'Saleshead', 'Figureshead')")
    public String updateFiguresSave(@ModelAttribute("figuresDataDto") @Valid FiguresDataDto figuresDataDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "figuresEdit"; 
        }
        FiguresData figuresData = figuresDataService.getFiguresDataById(figuresDataDto.getId());
        if (figuresData != null) {
            figuresData.setDate(figuresDataDto.getDate());
            figuresData.setProductName(figuresDataDto.getProductName());
            figuresData.setQuantitySold(figuresDataDto.getQuantitySold());
            double productPrice = figuresDataService.getProductPrice(figuresDataDto.getProductName());
            double totalRevenue = productPrice * figuresDataDto.getQuantitySold();
            figuresData.setTotalRevenue(totalRevenue);
            figuresDataService.saveFiguresData(figuresData);
        } else {
        }
        return "redirect:/figures";
    }

    @GetMapping("/viewSalesFigures")
    @PreAuthorize("hasAnyRole('HO_Company', 'Saleshead', 'Figureshead')")
    public String viewSalesFigures(Model model) {
        return "figures";
    }

    private FiguresData convertDtoToEntity(FiguresDataDto figuresDataDto) {
        FiguresData figuresData = new FiguresData();

        figuresData.setProductName(figuresDataDto.getProductName());
        figuresData.setQuantitySold(figuresDataDto.getQuantitySold());
        figuresData.setTotalRevenue(figuresDataDto.getTotalRevenue());
        figuresData.setDate(figuresDataDto.getDate());
        return figuresData;
    }

    @DeleteMapping("/deleteFigure/{id}")
    @PreAuthorize("hasAnyRole('HO_Company', 'Saleshead', 'Figureshead')")
    public ResponseEntity<String> deleteFigureById(@PathVariable Long id) {
        try {
            // Delete the record from the database using the service layer
            figuresDataService.deleteFigureById(id);
            return ResponseEntity.ok().body("Record deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting record: " + e.getMessage());
        }
    }

}
