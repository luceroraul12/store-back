package distribuidora.scrapping;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class SeleniumGridTest {
    @Test
    public void runTestOnDocker() throws Exception {
        DesiredCapabilities dcap = new DesiredCapabilities("firefox", "111.0", Platform.LINUX);
        System.setProperty("webdriver.gecko.driver","/home/homitowen/webDriver/geckodriver");
        // You should check the Port No here.
        URL gamelan = new URL("http://172.17.0.2:4444/wd/hub");
        WebDriver driver = new RemoteWebDriver(gamelan, dcap);
        // Get URL
        driver.get("http://gglobal.net.ar/bernal/?cliente");
        // Print Title
        String template = driver.getPageSource();
        System.out.println(template);
        driver.quit();
    }
}
