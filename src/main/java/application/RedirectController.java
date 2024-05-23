package application;

import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping("/redirect")
    public String redirect(Authentication authentication) {
        if (authentication != null) {
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            System.out.println("User Roles: " + roles);

            if (roles.contains("HO_Company")) {
                System.out.println("Redirecting to /home");
                return "redirect:/home";
            } else if (roles.contains("Financehead")) {
                System.out.println("Redirecting to /financeDashboard");
                return "redirect:/financeDashboard";
            } else if (roles.contains("Saleshead")) {
                System.out.println("Redirecting to /salesDashboard");
                return "redirect:/salesDashboard";           
            } else if (roles.contains("Operationalhead")) {
                System.out.println("Redirecting to /operationalDashboard");
                return "redirect:/operationalDashboard";
            } else if (roles.contains("Rateshead")) {
                System.out.println("Redirecting to /ratesDashboard");
                return "redirect:/ratesDashboard";
            } else if (roles.contains("Figureshead")) {
                System.out.println("Redirecting to /figures");
                return "redirect:/figures";
            } else if (roles.contains("Conversionhead")) {
                System.out.println("Redirecting to /conversionDashboard");
                return "redirect:/conversionDashboard";
            } else if (roles.contains("Forecastinghead")) {
                System.out.println("Redirecting to /forecastingDashboard");
                return "redirect:/forecastingDashboard";
            } else if (roles.contains("Budgetinghead")) {
                System.out.println("Redirecting to /financeBudgeting");
                return "redirect:/financeBudgeting";
            }else if (roles.contains("Producthead")) {
                System.out.println("Redirecting to /showProduct");
                return "redirect:/showProduct";
            }else if (roles.contains("Categoryhead")) {
                System.out.println("Redirecting to /category");
                return "redirect:/category";
            }
        }

        System.out.println("Redirecting to /login");
        return "redirect:/login";
    }
}
