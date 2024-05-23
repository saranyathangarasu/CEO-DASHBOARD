package application;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import application.model.Budgeting;
import application.service.BudgetingService;

@Controller
public class BudgetingController {

    @Autowired
    private BudgetingService budgetingService;
    @GetMapping("/financeBudgeting")
    public String getBudgetingPage(Model model) {
        List<Budgeting> budgetingDataList = budgetingService.getAllBudgetingData();
        model.addAttribute("budgetingDataList", budgetingDataList);
        model.addAttribute("budgeting", new Budgeting()); // Add an empty budgeting object
        return "financeBudgeting";
    }

    @PostMapping("/budgetingAdd")
    public String addBudgeting(@ModelAttribute("budgeting") Budgeting budgeting) {
        try {
            budgetingService.saveBudgeting(budgeting);
            return "redirect:/financeBudgeting"; // Redirect to the budgeting page
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception properly, perhaps show an error message
            return "errorPage"; // Redirect to an error page
        }
    }
    @GetMapping("/budgetingEdit/{budgetingId}")
    public String editBudgeting(@PathVariable Long budgetingId, Model model) {
        Optional<Budgeting> optionalBudgeting = budgetingService.findById(budgetingId);
        if (optionalBudgeting.isPresent()) {
            Budgeting budgeting = optionalBudgeting.get();
            model.addAttribute("budgeting", budgeting);
            return "financebudgetingEdit"; // Return the edit page
        } else {
            return "budgetingNotFound"; // Handle case where budgeting data is not found
        }
    }

    @PostMapping("/budgetingUpdate")
    public String updateBudgeting(@ModelAttribute("budgeting") Budgeting budgeting) {
        try {
            // Assuming budgeting.getId() retrieves the ID of the existing budgeting data
            Optional<Budgeting> existingBudgetingOptional = budgetingService.findById(budgeting.getId());
            if (existingBudgetingOptional.isPresent()) {
                Budgeting existingBudgeting = existingBudgetingOptional.get();
                // Update the existing budgeting data with the new values
                existingBudgeting.setDate(budgeting.getDate());
                existingBudgeting.setCategory(budgeting.getCategory());
                existingBudgeting.setPlannedAmount(budgeting.getPlannedAmount());
                existingBudgeting.setActualAmount(budgeting.getActualAmount());
                budgetingService.saveBudgeting(existingBudgeting); // Save the updated data
                return "redirect:/financeBudgeting"; // Redirect to the main budgeting page
            } else {
                // Handle case where existing budgeting data is not found
                return "budgetingNotFound";
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception properly, perhaps show an error message
            return "errorPage"; // Redirect to an error page
        }
    }
    @GetMapping("/api/budgeting")
    @ResponseBody
    public List<Budgeting> getAllBudgetingData() {
        return budgetingService.getAllBudgetingData();
    }
    
    @DeleteMapping("/budgetingDelete/{id}")
    public ResponseEntity<String> deleteBudgetingData(@PathVariable Long id) {
        try {
            budgetingService.deleteById(id);
            return ResponseEntity.ok("Budgeting data with ID " + id + " deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete budgeting data with ID " + id);
        }
    }


}
