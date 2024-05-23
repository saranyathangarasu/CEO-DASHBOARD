package application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import application.service.CustomUserDetailsServices;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsServices customUserDetailServices;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests()
                .requestMatchers("/index", "/register","/forecastingDashboard","/financeBudgeting","/forgotPassword",
                             "/changePassword","/chatRoom10","/termsAndConditions"
                              ).permitAll()
                .requestMatchers("/home", "/chatRoom").hasAuthority("HO_Company")
                .requestMatchers("/financeDashboard", "/financeUpdate", "/financeEdit", "/profile1").hasAnyAuthority("HO_Company","Financehead")
                .requestMatchers("/financeBudgeting").hasAnyAuthority("HO_Company","Financehead")
                .requestMatchers("/salesDashboard", "/salesUpdate", "/salesEdit").hasAnyAuthority("HO_Company","Saleshead")
                .requestMatchers("/operationalDashboard", "/operationalUpdate", "/operationalEdit").hasAnyAuthority("HO_Company","Operationalhead")
                .requestMatchers("/ratesDashboard", "/ratesEdit").hasAnyAuthority("HO_Company","Saleshead","Rateshead")
                .requestMatchers("/figures", "/figuresUpdate", "/figuresEdit","/revenueDetails").hasAnyAuthority("HO_Company","Saleshead","Figureshead")
                .requestMatchers("/conversionDashboard", "/conversionUpdate", "/conversionEdit").hasAnyAuthority("HO_Company","Saleshead","Conversionhead")
                .requestMatchers("/showProduct", "/addProduct", "/updateProduct").hasAnyAuthority("HO_Company","Saleshead","Figureshead", "Producthead")
                .requestMatchers("/chatRoom1").hasAnyAuthority("HO_Company","Financehead")
                .requestMatchers("/chatRoom2").hasAnyAuthority("HO_Company","Saleshead")
                .requestMatchers("/chatRoom3").hasAnyAuthority("HO_Company","Operationalhead")
                .requestMatchers("/chatRoom4").hasAnyAuthority("HO_Company","Saleshead","Conversionhead")
                .requestMatchers("/chatRoom5").hasAnyAuthority("HO_Company","Saleshead","Figureshead")
                .requestMatchers("/chatRoom6").hasAnyAuthority("HO_Company","Saleshead","Rateshead")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/redirect") 
                .loginProcessingUrl("/login")
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/redirect") 
                .permitAll();


        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(customUserDetailServices)
            .passwordEncoder(passwordEncoder());
    }

}
