package automationcraft.engine.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.Set;

public class SeleniumBase {
    private WebDriver driver;
    //atributo

    private WebDriverWait wait;

    //Constructor
    public SeleniumBase(WebDriver driver) {
        this.driver = DriverFactory.getDriver();
    }

    public WebDriver getDriver(WebDriver driver) {
        return this.driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = DriverFactory.getDriver();
    }

    //metodos -> Wrapper o envoltorios : enmascarar tecnologia de Selenium
    public WebElement findElement(By locator) {
        return DriverFactory.getDriver().findElement(locator);
    }

    public void manejoAlerta() {
        driver.switchTo().alert().accept();
    }

    public void switchTo () {
        String firstTab = DriverFactory.getDriver().getWindowHandle();
        Set<String> tabs = DriverFactory.getDriver().getWindowHandles();
        for (String actual : tabs) {
            if (!actual.equalsIgnoreCase(firstTab)) {
                System.out.println("Cambio de tab en chrome...");
                DriverFactory.getDriver().switchTo().window(actual);
                break;
            } else {
                System.out.println("Buscando nueva Tab...");
            }
        }
    }

    public void enter (By locator){
        DriverFactory.getDriver().findElement(locator).sendKeys(Keys.ENTER);
    }

    public List<WebElement> findElements(By locator) {
        return DriverFactory.getDriver().findElements(locator);
    }

    public String getText(By locator) {
        return DriverFactory.getDriver().findElement(locator).getText();
    }

    public int getsize(By locator) {
        return DriverFactory.getDriver().findElements(locator).size();
    }

    public void type(String inputText, By locator) {
        DriverFactory.getDriver().findElement(locator).sendKeys(inputText);
    }

    public void click(By locator){
        DriverFactory.getDriver().findElement(locator).click();
    }

    public Boolean isDisplayed (By locator){
        try {
            return DriverFactory.getDriver().findElement(locator).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public void goToUrl (String url){
        DriverFactory.getDriver().get(url);
    }

    public String getTitle () {
        return DriverFactory.getDriver().getTitle();
    }

    //este metodo creado es para buscar elementos por el localizador
    public WebElement buscarElementoWeb (By localizador){
        return DriverFactory.getDriver().findElement(localizador);
    }

    //Buscar una lista de elementos web
    public List<WebElement> buscarElementosWeb (By localizador){
        return DriverFactory.getDriver().findElements(localizador);
    }

    //Crea un metodo para trabajar con elementos web
    public void click (WebElement elemento){
        elemento.click();
    }

    //Crear metodo de espera implicita
    public void esperarNsegundos ( int miliSegundos){
        try {
            Thread.sleep(miliSegundos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Crear metodo de espera explicita
    public WebElement esperaExplicita (By localizador){
        wait = new WebDriverWait(DriverFactory.getDriver(), 20);
        return wait.until(ExpectedConditions.presenceOfElementLocated(localizador));
    }

    //espera explicita
    public WebElement esperarPorElementoAClickear (By localizador){
        wait = new WebDriverWait(DriverFactory.getDriver(), 20);

        return wait.until(ExpectedConditions.elementToBeClickable(localizador));
    }


    public String getCurrentUrl () {
        return driver.getCurrentUrl();
    }

    public void validacionURL (String cadena) {
        assertEquals(cadena, getCurrentUrl());
    }

    public void validarBoton (By locator) {
        if (locator != null) {
            assertTrue(true);
        } else {
            assertFalse(false);
        }
    }

    public void validacionEquals (By locator, String cadena) {
        if (getText(locator).equals(cadena)) {
            assertTrue(true);
        } else {
            assertFalse(false);
        }
    }

}
