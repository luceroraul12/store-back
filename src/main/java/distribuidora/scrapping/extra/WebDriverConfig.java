package distribuidora.scrapping.extra;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Configuracion del Web driver de Selenium.
 */
//@Configuration
public class WebDriverConfig {

//    @PostConstruct
    /**
     * Propongo una carpeta donde deben estar almacenado los drivers
     */
    public void postConstrcut() throws MalformedURLException {
        String systemOperative = System.getProperty("os.name");
        if (systemOperative.equals("Windows")){
            System.setProperty("webdriver.edge.driver","C:\\selenium\\msedgedriver.exe");
            System.setProperty("webdriver.chrome.driver","C:\\selenium\\chromedriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver","/home/homitowen/webDriver/geckodriver");
        }
    }

//    @Bean()
//    @Scope("prototype")
    public WebDriver driver(){
        return getDriver();
    }

    /**
     * Por polimorfismo devuelve un WebDriver.
     * Puede ser de Edge o Chrome segun el navegador que tenga instalado en el computador
     */
    private WebDriver getDriver(){
        ChromiumOptions options = null;
        WebDriver driver = null;
        try{
            System.out.println("usando edge driver");
            options = new EdgeOptions();
            options.setPageLoadStrategy(PageLoadStrategy.NONE);
            driver = new EdgeDriver(options);
            driver.manage().window().minimize();
            driver.getWindowHandle();
        } catch (Exception e) {
            System.out.println("no es edge");
        }
        try{
            System.out.println("usando chrome driver");
            options = new ChromeOptions();
            options.setPageLoadStrategy(PageLoadStrategy.NONE);
            driver = new ChromeDriver();
            driver.manage().window().minimize();
            driver.getWindowHandle();
        } catch (Exception e) {
            System.out.println("no es chrome");

        }
        try{
            System.out.println("usando firefox driver");
            DesiredCapabilities dcap = new DesiredCapabilities("firefox", "111.0", Platform.LINUX);
            URL gamelan = new URL("http://172.17.0.2:4444/wd/hub");
            driver = new RemoteWebDriver(gamelan, dcap);
        } catch (Exception e) {
            System.out.println("no es firefox");
        }
        return driver;
    }
}
