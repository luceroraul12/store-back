package distribuidora.scrapping.extra;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Configuration
public class WebDriverConfig {

    @PostConstruct
    void postConstrcut(){
        System.setProperty("webdriver.edge.driver","C:\\selenium\\msedgedriver.exe");
    }

    @Bean
    public EdgeDriver driver(){
        EdgeDriver driver = new EdgeDriver();
        return driver;
    }
}
