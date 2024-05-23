package application;

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

import application.Dto.ConversionDataDto;
import application.model.ConversionData;
import application.service.ConversionDataService;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Controller
public class ConversionDataController {

    private final ConversionDataService conversionDataService;

    @Autowired
    public ConversionDataController(ConversionDataService conversionDataService) {
        this.conversionDataService = conversionDataService;
    }

    @GetMapping("/conversionData")
    @PreAuthorize("hasAnyRole('CEO', 'Saleshead', 'Conversionhead')")
    public ResponseEntity<List<ConversionData>> getConversionData() {
        List<ConversionData> conversionDataList = conversionDataService.getAllConversionData();
        return new ResponseEntity<>(conversionDataList, HttpStatus.OK);
    }

    @GetMapping("/conversionUpdate")
    @PreAuthorize("hasAnyRole('CEO', 'Saleshead', 'Conversionhead')")
    public String showConversionUpdateForm(Model model) {
        model.addAttribute("conversionDataDto", new ConversionDataDto());
        return "conversionUpdate"; 
    }

    @GetMapping("/conversionUpdateById")
    @PreAuthorize("hasAnyRole('CEO', 'Saleshead', 'Conversionhead')")
    public String showConversionUpdateFormById(@RequestParam("id") Long id, Model model) {
        ConversionData conversionData = conversionDataService.getConversionDataById(id);
        if (conversionData != null) {
            model.addAttribute("conversionDataDto", convertEntityToDto(conversionData));
        } else {
            model.addAttribute("conversionDataDto", new ConversionDataDto());
        }
        return "conversionUpdate";
    }
    
    @PostMapping("/addConversion")
    @PreAuthorize("hasAnyRole('CEO', 'Saleshead', 'Conversionhead')")
    public String addConversion(@Valid @ModelAttribute("conversionDataDto") ConversionDataDto conversionDataDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "conversionUpdate";
        }
        ConversionData newConversionData = convertDtoToEntity(conversionDataDto);
        double conversionRate = calculateConversionRate(conversionDataDto.getNoOfConversions(), conversionDataDto.getTotalNumberOfVisitors());
        newConversionData.setConversionRate(conversionRate);
        conversionDataService.saveConversionData(newConversionData);
        return "redirect:/conversionDashboard";
    }

    @GetMapping("/conversionDashboard")
    @PreAuthorize("hasAnyRole('CEO', 'Saleshead', 'Conversionhead')")
    public String showConversionDashboard(Model model) {
        try {
            List<ConversionData> conversionDataList = conversionDataService.getAllConversionData();
            model.addAttribute("conversionDataList", conversionDataList);
            return "conversionDashboard";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    @GetMapping("/conversionEdit/{id}")
    @PreAuthorize("hasAnyRole('CEO', 'Saleshead', 'Conversionhead')")
    public String showConversionEditForm(@PathVariable("id") Long id, Model model) {
        ConversionData existingData = conversionDataService.getConversionDataById(id);
        ConversionDataDto conversionDataDto = new ConversionDataDto();
        conversionDataDto.setId(existingData.getId());
        conversionDataDto.setDate(existingData.getDate());
        conversionDataDto.setNoOfConversions(existingData.getNoOfConversions());
        conversionDataDto.setTotalNumberOfVisitors(existingData.getTotalNumberOfVisitors());
        model.addAttribute("conversionDataDto", conversionDataDto);    
        return "conversionEdit";
    }

    @PostMapping("/conversionUpdateSave")
    @PreAuthorize("hasAnyRole('CEO', 'Saleshead', 'Conversionhead')")
    public String conversionUpdateSave(@Valid @ModelAttribute("conversionDataDto") ConversionDataDto conversionDataDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "conversionEdit"; 
        }
        if (conversionDataDto.getId() == null) {
            return "error";
        }
        ConversionData existingData = conversionDataService.getConversionDataById(conversionDataDto.getId());
        if (existingData != null) {
            existingData.setDate(conversionDataDto.getDate());
            existingData.setNoOfConversions(conversionDataDto.getNoOfConversions());
            existingData.setTotalNumberOfVisitors(conversionDataDto.getTotalNumberOfVisitors());
            double conversionRate = calculateConversionRate(conversionDataDto.getNoOfConversions(), conversionDataDto.getTotalNumberOfVisitors());
            existingData.setConversionRate(conversionRate);
            conversionDataService.saveConversionData(existingData);
        } else {
        }
        return "redirect:/conversionDashboard";
    }

    @GetMapping("/conversionEdit")
    public String showConversionEditForm(@RequestParam("id") Long id,
                                         @RequestParam("date") String dateString,
                                         @RequestParam("noOfConversions") int noOfConversions,
                                         @RequestParam("totalNumberOfVisitors") int totalNumberOfVisitors,
                                         @RequestParam("conversionRate") double conversionRate,
                                         Model model) {
        ConversionDataDto conversionDataDto = new ConversionDataDto();
        conversionDataDto.setId(id);
        model.addAttribute("id", id);
        model.addAttribute("date", dateString);
        model.addAttribute("noOfConversions", noOfConversions);
        model.addAttribute("totalNumberOfVisitors", totalNumberOfVisitors);
        model.addAttribute("conversionRate", conversionRate);
        model.addAttribute("conversionDataDto", conversionDataDto);
        return "conversionEdit";
    }
    
    private double calculateConversionRate(int noOfConversions, int totalNumberOfVisitors) {
        if (totalNumberOfVisitors > 0) {
            return ((double) noOfConversions / totalNumberOfVisitors) * 100;
        } else {
            return 0.0;
        }
    }
    @DeleteMapping("/deleteConversion/{id}")
    @PreAuthorize("hasAnyRole('CEO', 'Saleshead', 'Conversionhead')")
    public ResponseEntity<String> deleteConversion(@PathVariable("id") Long id) {
        try {
            conversionDataService.deleteConversionDataById(id);
            return new ResponseEntity<>("Record deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete record", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ConversionData convertDtoToEntity(ConversionDataDto conversionDataDto) {
        ConversionData conversionData = new ConversionData();
        conversionData.setDate(conversionDataDto.getDate());
        conversionData.setNoOfConversions(conversionDataDto.getNoOfConversions());
        conversionData.setTotalNumberOfVisitors(conversionDataDto.getTotalNumberOfVisitors());
        conversionData.setConversionRate(conversionDataDto.getConversionRate());
        return conversionData;
    }
    
    private ConversionDataDto convertEntityToDto(ConversionData conversionData) {
        ConversionDataDto conversionDataDto = new ConversionDataDto();
        conversionDataDto.setId(conversionData.getId());
        conversionDataDto.setDate(conversionData.getDate());
        conversionDataDto.setNoOfConversions(conversionData.getNoOfConversions());
        conversionDataDto.setTotalNumberOfVisitors(conversionData.getTotalNumberOfVisitors());
        conversionDataDto.setConversionRate(conversionData.getConversionRate());
        return conversionDataDto;
    }
}
