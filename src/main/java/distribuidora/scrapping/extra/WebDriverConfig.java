package distribuidora.scrapping.extra;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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
        String systemOperative = System.getProperty("os.name");
        if (systemOperative.equals("Windows")){
            System.setProperty("webdriver.edge.driver","C:\\selenium\\msedgedriver.exe");
            System.setProperty("webdriver.chrome.driver","C:\\selenium\\chromedriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver","/home/homitowen/webDriver/geckodriver");
        }
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
        WebDriver driver = null;
        try{
            System.out.println("usando edge driver");
            driver = new EdgeDriver();
        } catch (Exception e) {
            System.out.println("no es edge");
        }
        try{
            System.out.println("usando chrome driver");
            driver = new ChromeDriver();
        } catch (Exception e) {
            System.out.println("no es chrome");

        }
        try{
            System.out.println("usando firefox driver");
            driver = new FirefoxDriver();
        } catch (Exception e) {
            System.out.println("no es firefox");
        }
        driver.manage().window().minimize();
        driver.getWindowHandle();
        return driver;
    }
}
