package distribuidora.scrapping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
public class ScrappingApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ScrappingApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ScrappingApplication.class, args);
	}

}
