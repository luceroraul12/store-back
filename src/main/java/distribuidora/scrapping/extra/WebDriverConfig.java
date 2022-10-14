package distribuidora.scrapping.extra;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class WebDriverConfig {

    @PostConstruct
    void postConstrcut(){
        System.setProperty("webdriver.edge.driver","C:\\selenium\\msedgedriver.exe");
        System.setProperty("webdriver.chrome.driver","C:\\selenium\\chromedriver.exe");

    }

    @Bean
    public WebDriver driver(){
        WebDriver driver;
        try{
            System.out.println("usando edge driver");
            driver = new EdgeDriver();
        } catch (Exception e) {
            System.out.println("usando chrome driver");
            driver = new ChromeDriver();
        }
        driver.manage().window().minimize();
        driver.getWindowHandle();
        return driver;
    }
}
