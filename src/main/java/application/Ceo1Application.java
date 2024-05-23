package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class Ceo1Application {

	public static void main(String[] args) {
		SpringApplication.run(Ceo1Application.class, args);
		System.out.println("Application");
	}

}
