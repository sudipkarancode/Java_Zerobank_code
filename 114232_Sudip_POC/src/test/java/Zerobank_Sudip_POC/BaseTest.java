package Zerobank_Sudip_POC;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
    public static WebDriver driver;
    @BeforeClass
    @Parameters("browser")
    public void setUp(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
            	// Initialize WebDriverManager for ChromeDriver
                WebDriverManager.chromedriver().setup();
                // Create a new instance of ChromeDriver
                driver = new ChromeDriver();
                System.out.println("User Selected Browser as Chrome");
                break;
                
            case "firefox":
            	WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                System.out.println("User Selected Browser as Firefox");
                break;
                
            case "edge":
            	WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                System.out.println("User Selected Browser as Edge");
                break;
            default:
                throw new IllegalArgumentException("oops Browser not supported: " + browser);
        }
        driver.manage().window().maximize();
        driver.get("http://zero.webappsecurity.com/index.html");
        }


    //@AfterClass
    //public void tearDown() {
        // Close the browser
        //if (driver != null) {
            //driver.quit();
   //
}
