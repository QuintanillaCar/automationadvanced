package automationcraft.engine.bdd;

import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import automationcraft.engine.utils.LoadProperties;
import automationcraft.engine.selenium.DriverFactory;

import java.net.MalformedURLException;
import java.util.Properties;

public class ApplicationHooks {
    private DriverFactory driverFactory;
    private WebDriver driver;
    Properties prop;


    @Before(order = 0)
    public void getProperty(){
        prop = LoadProperties.init_prop();
    }
    @Before(order =1)
    public void launchBrowser() throws MalformedURLException {
        String browserName = prop.getProperty("browser");
        String remoteDriver = (prop.getProperty("remoteDriver"));
        driverFactory = new DriverFactory();
        driver = driverFactory.init_driver(browserName,remoteDriver);
    }

    @After(order=0)
    public void quitBrowser(){
        driver.quit();
    }

    @After(order=1)
    public void tearDown(Scenario scenario){
        if(scenario.isFailed()){
            //take screenshot:
            String screenshotName = scenario.getName().replaceAll(" ","_");
            byte [] sourcePath = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(sourcePath,"image/png",screenshotName);
        }
    }


}