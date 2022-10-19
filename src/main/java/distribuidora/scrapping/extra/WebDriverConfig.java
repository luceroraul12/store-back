package distribuidora.scrapping.extra;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Configuracion del Web driver de Selenium.
 */
@Configuration
public class WebDriverConfig {

    @PostConstruct
    /**
     * Propongo una carpeta donde deben estar almacenado los drivers
     */
    void postConstrcut(){
        System.setProperty("webdriver.edge.driver","C:\\selenium\\msedgedriver.exe");
        System.setProperty("webdriver.chrome.driver","C:\\selenium\\chromedriver.exe");
    }

    @Bean

    public WebDriver driver(){
        return getDriver();
    }

    /**
     * Por polimorfismo devuelve un WebDriver.
     * Puede ser de Edge o Chrome segun el navegador que tenga instalado en el computador
     */
    private WebDriver getDriver(){
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
