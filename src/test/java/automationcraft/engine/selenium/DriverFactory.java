package automationcraft.engine.selenium;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DriverFactory {

    public WebDriver driver;
    public static ThreadLocal<WebDriver> hiloLocal = new ThreadLocal<>();

    /**
     * inicializa el WebDriver segun la seleccion del browser
     * @param browser: chrome | firefox
     * @return Webdriver
     */
    public WebDriver init_driver(String browser,String remoteDriver) throws MalformedURLException {
        System.out.println("browser value is: "+browser);

        if(browser.equals("chrome")&& remoteDriver.equals("false")){
            WebDriverManager.chromedriver().setup();
            hiloLocal.set(new ChromeDriver());
        }else if(browser.equals("firefox")&& remoteDriver.equals("false")){
            WebDriverManager.firefoxdriver().setup();
            hiloLocal.set(new FirefoxDriver());
        }else if(browser.equals("chrome")&& remoteDriver.equals("true")){
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
            desiredCapabilities.setCapability(CapabilityType.PLATFORM_NAME, Platform.LINUX);
            desiredCapabilities.setCapability("tz", "America/Santiago");
            hiloLocal.set(new RemoteWebDriver(new URL("http://zalenium-webappspring.apps.ocp.tsoftlabs.com/wd/hub"), desiredCapabilities));
        }else if(browser.equals("firefox")&& remoteDriver.equals("true")){
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, BrowserType.FIREFOX);
            desiredCapabilities.setCapability(CapabilityType.PLATFORM_NAME, Platform.LINUX);
            desiredCapabilities.setCapability("tz", "America/Santiago");
            hiloLocal.set(new RemoteWebDriver(new URL("http://zalenium-webappspring.apps.ocp.tsoftlabs.com/wd/hub"), desiredCapabilities));
        }else {
            System.out.println("Please pass the correct browser value: "+browser);
        }
        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        return getDriver();
    }



    /**
     * retorna el WebDriver desde el ThreadLocal
     * @return WebDriver
     */
    public static synchronized WebDriver getDriver(){
        return hiloLocal.get();
    }
}
