package application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PathVariable;
import application.model.OperationalData;
import application.service.OperationalDataService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

import java.util.List;

@Controller
public class OperationalDataController {

    private final Logger logger = LoggerFactory.getLogger(OperationalDataController.class);

    private final OperationalDataService operationalDataService;

    @Autowired
    public OperationalDataController(OperationalDataService operationalDataService) {
        this.operationalDataService = operationalDataService;
    }
    
    @GetMapping("/operationalDashboard")
    @PreAuthorize("hasAnyRole('CEO', 'Operationalhead')")
    public String operationalDashboard(Model model) {
        try {
            List<OperationalData> operationalDataList = operationalDataService.getAllOperationalData();
            model.addAttribute("operationalDataList", operationalDataList);
            operationalDataList.forEach(data -> logger.info("Operational Data: {}", data));
            return "operationalDashboard";
        } catch (Exception e) {
            logger.error("Error in operationalDashboard", e);
            throw e;
        }
    }
    
    @GetMapping("/operationalUpdate")
    @PreAuthorize("hasAnyRole('CEO', 'Operationalhead')")
    public String showOperationalUpdateForm(Model model) {
        OperationalData operationalData = new OperationalData();
        model.addAttribute("operationalData", operationalData);
        return "operationalUpdate";
    }
    
    @GetMapping("/operationalEdit")
    @PreAuthorize("hasAnyRole('CEO', 'Operationalhead')")
    public String showOperationalEditForm(@RequestParam Long id, Model model) {
        try {           
            OperationalData operationalData = operationalDataService.getOperationalDataById(id);
            model.addAttribute("operationalData", operationalData);
            return "operationalEdit";
        } catch (Exception e) {
            logger.error("Error retrieving operational data for edit", e);
            throw e;
        }
    }

    @PostMapping("/operationalUpdate")
    @PreAuthorize("hasAnyRole('CEO', 'Operationalhead')")
    public String operationalUpdate(@ModelAttribute("operationalData") OperationalData updatedData, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "operationalEdit";
        }
        try {
            OperationalData existingData = operationalDataService.getOperationalDataById(updatedData.getId());
            existingData.setDate(updatedData.getDate());
            existingData.setName(updatedData.getName());
            existingData.setValue(updatedData.getValue());
            operationalDataService.saveOperationalData(existingData);
            return "redirect:/operationalDashboard";
        } catch (ValidationException ve) {
            logger.error("Validation Error in operationalUpdate", ve);
            throw ve;
        } catch (Exception e) {
            logger.error("Error in operationalUpdate", e);
            throw e;
        }
    }

    @GetMapping("/api/operationalData")
    @PreAuthorize("hasAnyRole('CEO', 'Operationalhead')")
    public ResponseEntity<List<OperationalData>> getOperationalData() {
        try {
            List<OperationalData> operationalDataList = operationalDataService.getAllOperationalData();
            return ResponseEntity.ok(operationalDataList);
        } catch (Exception e) {
            logger.error("Error fetching operational data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PostMapping("/operationalUpdateSave")
    @PreAuthorize("hasAnyRole('CEO', 'Operationalhead')")
    public String operationalUpdateSave(@Valid @ModelAttribute("operationalData") OperationalData operationalData, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "operationalUpdate";
        }
        try {
            operationalDataService.saveOperationalData(operationalData);
            return "redirect:/operationalDashboard";
        } catch (ValidationException ve) {
            logger.error("Validation Error in operationalUpdateSave", ve);
            throw ve;
        } catch (Exception e) {
            logger.error("Error in operationalUpdateSave", e);
            throw e;
        }
    }

    @DeleteMapping("/operationalDelete/{id}")
    @PreAuthorize("hasAnyRole('CEO', 'OperationalManager')")
    public ResponseEntity<String> deleteOperationalData(@PathVariable Long id) {
        operationalDataService.deleteById(id);
        return ResponseEntity.ok("Operational data with ID " + id + " deleted successfully");
    }

    @GetMapping("/operational-logout")
    public String logout() {
        return "logout";
    }

}