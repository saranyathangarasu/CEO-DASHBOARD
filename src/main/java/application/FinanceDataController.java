package application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import application.Dto.FinanceDataDto;
import application.Dto.FinancialRatioDto;
import application.model.Category;
import application.model.FinanceData;
import application.service.CategoryService;
import application.service.FinanceDataService;

import jakarta.validation.Valid;

@Controller
public class FinanceDataController {

	@Autowired
    private FinanceDataService financeDataService;

    @Autowired
    public FinanceDataController(FinanceDataService financeDataService) {
        this.financeDataService = financeDataService;
    }
    
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/financeDashboard")
    @PreAuthorize("hasAnyRole('CEO', 'Financehead')")
    public String financeDashboard(Model model, Authentication authentication) {
        String viewType = determineViewType(authentication);
        model.addAttribute("CEO", isCEO(authentication));
        model.addAttribute("viewType", viewType);

        List<FinanceData> financeDataList = financeDataService.findAll();
        FinanceData latestFinanceData = financeDataService.getFinanceData();
        List<FinancialRatioDto> financialRatios = financeDataService.calculateFinancialRatios(financeDataList);

        model.addAttribute("financeDataList", financeDataList);
        model.addAttribute("latestFinanceData", latestFinanceData);
        model.addAttribute("financialRatios", financialRatios);

        return "financeDashboard";
    }

    @GetMapping("/netProfitMarginData")
    @PreAuthorize("hasAnyRole('CEO', 'Financehead')")
    public ResponseEntity<List<FinancialRatioDto>> getNetProfitMarginData() {
        List<FinancialRatioDto> netProfitMarginData = financeDataService.calculateNetProfitMarginData();
        return ResponseEntity.ok(netProfitMarginData);
    }
    
    @GetMapping("/currentRatioData")
    @PreAuthorize("hasAnyRole('CEO', 'Financehead')")
    @ResponseBody
    public ResponseEntity<List<FinancialRatioDto>> getCurrentRatioData() {
        List<FinancialRatioDto> currentRatioData = financeDataService.calculateCurrentRatioData(); 
        return ResponseEntity.ok(currentRatioData);
    }

    @GetMapping("/debtToEquityRatioData")
    @PreAuthorize("hasAnyRole('CEO', 'Financehead')")
    @ResponseBody
    public ResponseEntity<List<FinancialRatioDto>> getDebtToEquityRatioData() {
        List<FinancialRatioDto> debtToEquityRatioData = financeDataService.calculateDebtToEquityRatioData(); 
        return ResponseEntity.ok(debtToEquityRatioData);
    }
    
    @GetMapping("/api/financeData/{id}")
    @PreAuthorize("hasAnyRole('CEO', 'Financehead')")
    public ResponseEntity<FinanceData> getFinanceDataById(@PathVariable Long id) {
        FinanceData financeData = financeDataService.findById(id);
        if (financeData != null) {
            return ResponseEntity.ok(financeData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/financeUpdate")
    @PreAuthorize("hasAnyRole('CEO', 'Financehead')")
    public String showFinanceUpdateForm(@RequestParam(value = "id", required = false) Long id, Model model) {
        FinanceDataDto financeDataDto = new FinanceDataDto();

        if (id != null) {
            FinanceData financeData = financeDataService.findById(id);
            if (financeData != null) {
                financeDataDto = new FinanceDataDto(financeData);
                model.addAttribute("isUpdate", true);
            } else {
                return "redirect:/financeDashboard";
            }
        } else {
            model.addAttribute("isUpdate", false);
        }

        model.addAttribute("financeDataDto", financeDataDto);
        return "financeUpdate";
    }
    
    @GetMapping("/financeEdit/{id}")
    @PreAuthorize("hasAnyRole('CEO', 'Financehead')")
    public String showFinanceEditForm(@PathVariable Long id, Model model) {
        FinanceData financeData = financeDataService.findById(id);
        if (financeData != null) {
            FinanceDataDto financeDataDto = new FinanceDataDto(financeData);
            model.addAttribute("isUpdate", true);
            model.addAttribute("financeDataDto", financeDataDto);
        } else {
            return "redirect:/financeDashboard"; 
        }
        return "financeEdit";
    }

    @PostMapping("/financeUpdateSave")
    @PreAuthorize("hasAnyRole('CEO', 'Financehead')")
    public String processFinanceUpdateSaveForm(@Valid @ModelAttribute("financeDataDto") FinanceDataDto financeDataDto,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isUpdate", financeDataDto.getId() != null);
            return "financeUpdate"; 
        }
        if (financeDataDto.getId() == null) {
            financeDataService.save(financeDataDto); 
        } else {
            financeDataService.update(financeDataDto);
        }
        return "redirect:/financeDashboard"; 
    }

    @PostMapping("/financeUpdate")
    @PreAuthorize("hasAnyRole('CEO', 'Financehead')")
    public String processFinanceUpdateForm(@Valid @ModelAttribute("financeDataDto") FinanceDataDto financeDataDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "financeEdit";
        }
        if (financeDataDto.getId() != null) {
            financeDataService.update(financeDataDto); 
        } else {           
            return "redirect:/financeDashboard";
        }
        return "redirect:/financeDashboard";
    }
    
    @DeleteMapping("/financeDelete/{id}")
    @PreAuthorize("hasAnyRole('CEO', 'Financehead')")
    public ResponseEntity<String> deleteFinanceData(@PathVariable Long id){
        financeDataService.deleteById(id);
        return ResponseEntity.ok("Finance data with ID " + id + " deleted successfully");
    }
    
    @GetMapping("/finance-logout")
    public String financeLogout() {
        return "logout";
    }

    private String determineViewType(Authentication authentication) {
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CEO"))) {
            return "CEO";
        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_Financehead"))) {
            return "Financehead";
        } else {
            return "Unknown";
        }
    }

    private boolean isCEO(Authentication authentication) {
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CEO"));
    }
    
}
