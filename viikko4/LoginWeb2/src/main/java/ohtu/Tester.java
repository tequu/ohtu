
package ohtu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Tester {
    public static void main(String[] args) {
        WebDriver driver = new HtmlUnitDriver();

        driver.get("http://localhost:8080");
        System.out.println( driver.getPageSource() );
        WebElement element = driver.findElement(By.linkText("login"));       
        element.click(); 
        
        System.out.println("==");
        
        System.out.println( driver.getPageSource() );
        element = driver.findElement(By.name("username"));
        element.sendKeys("pekka");
        element = driver.findElement(By.name("password"));
        element.sendKeys("akkep");
        element = driver.findElement(By.name("login"));
        element.submit();
        
        System.out.println("==");
        System.out.println( driver.getPageSource() );
        
        /*Väärä salasana */
        driver.get("http://localhost:8080");
        element = driver.findElement(By.linkText("login"));       
        element.click(); 
        
        element = driver.findElement(By.name("username"));
        element.sendKeys("pekka");
        element = driver.findElement(By.name("password"));
        element.sendKeys("gfdasdf");
        element = driver.findElement(By.name("login"));
        element.submit();
        
        System.out.println("==");
        System.out.println( driver.getPageSource() );
        
        
        
        /** Väärä käyttäjätunnus */
        driver.get("http://localhost:8080");
        element = driver.findElement(By.linkText("login"));       
        element.click(); 
        
        element = driver.findElement(By.name("username"));
        element.sendKeys("pfdsa");
        element = driver.findElement(By.name("password"));
        element.sendKeys("akkep");
        element = driver.findElement(By.name("login"));
        element.submit();
        
        System.out.println("==");
        System.out.println( driver.getPageSource() );
        
        
        
        /** Uuden käyttäjän luonti */
        driver.get("http://localhost:8080");
        System.out.println("==");
        System.out.println( driver.getPageSource() );
        element = driver.findElement(By.linkText("register new user"));       
        element.click(); 
        
        element = driver.findElement(By.name("username"));
        element.sendKeys("testi");
        element = driver.findElement(By.name("password"));
        element.sendKeys("itset");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("itset");
        element = driver.findElement(By.name("add"));
        element.submit();
        
        System.out.println("==");
        System.out.println( driver.getPageSource() );
        
        
        
        /** Käyttäjän luonti ja kirjautuminen */
        driver.get("http://localhost:8080");
        element = driver.findElement(By.linkText("register new user"));       
        element.click(); 
        
        element = driver.findElement(By.name("username"));
        element.sendKeys("testi");
        element = driver.findElement(By.name("password"));
        element.sendKeys("itset123");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("itset123");
        element = driver.findElement(By.name("add"));
        element.submit();
        
        driver.get("http://localhost:8080");
        element = driver.findElement(By.linkText("login"));       
        element.click(); 
        
        element = driver.findElement(By.name("username"));
        element.sendKeys("testi");
        element = driver.findElement(By.name("password"));
        element.sendKeys("itset123");
        element = driver.findElement(By.name("login"));
        element.submit();
        
        System.out.println("==");
        System.out.println( driver.getPageSource() );
    }
}
