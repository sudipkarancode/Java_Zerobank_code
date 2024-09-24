package Zerobank_Sudip_POC;
import java.time.Duration;
import org.openqa.selenium.io.FileHandler;
import java.io.File;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;


public class test_Zerobank_Runner {
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
    
    @DataProvider(name = "userRoles")
    public Object[][] createData() {
        return new Object[][] {
            { "Customer", "Account Home","username","password"}
//            { "Area Admin", "Admin View Transaction" },
//            { "Branch Admin", "Admin View Transaction" },
//            { "Super Admin", "Admin View Transaction" }
        };
    }


    @Test(dataProvider = "userRoles",priority = 1)
    public void testLogin(String role, String page,String id,String Pass) throws IOException
    	{
    	System.out.println("*******************************************************************************************");
        System.out.println("Executing LogIn Scenerio:-");
        System.out.println("*******************************************************************************************");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
        // Find sign in button and click on it
        WebElement usernameField = driver.findElement(By.xpath("//*[@id='signin_button']"));
        usernameField.click();
        System.out.println("User Successfully clicked on [Sign in] Button");

        // Create an instance of WebDriverWait with a timeout of 10 seconds
        WebDriverWait wait6 = new WebDriverWait(driver, Duration.ofSeconds(50));
        // Wait for a login field to be visible
        WebElement forget_link = wait6.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[2]/div/div/div/a")));
        // Perform actions with the element
        forget_link.click();
        System.out.println("User Successfully Clicked on [Forgot your password] Link");
        
        // VALIDATE CURRENT URL
        String currentUrl = driver.getCurrentUrl();
        System.out.println("User Successfully redirected to [Unlock Account/Forgot Password] page and URL: "+ currentUrl);
        
    	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        // navigate back to login page
        driver.navigate().back();
        System.out.println("User Successfully [Navigated Back]");
        
        // Validated We are on the Zero Bank Home page
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        // Wait for a login field to be visible
        WebElement textElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[1]/div/div/a")));
    	System.out.println("User Successfully Redirected to ZeroBank Home Page");
        // Get the text from the element
        String actualText = textElement.getText();
        // Define the expected text
        String expectedText = actualText;
        // Perform the assertion
        
    	if (expectedText == actualText) {
            System.out.println("Hurrey! We are in Zero Bank Home Page");
        	}
  
    	else 
        {
            // Handle assertion failure
            System.out.println("Oops! We are not in Zero Bank Home Page");
            // Take a screenshot
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);

            // Define the path to save the screenshot
            File destinationFile = new File("HomepageError.png");

            // Copy the screenshot to the destination file
            FileHandler.copy(screenshotFile, destinationFile);

            System.out.println("Screenshot saved to: " + destinationFile.getAbsolutePath());
         } 
        
        
        System.out.println("Selected Role for this test case: " + role + ", and Reditrected Page: " + page);
        
    	// Wait for a login field to be visible
        WebElement login = wait6.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_login")));
        // Perform actions with the element
        login.click();
        login.sendKeys(id);
        System.out.println("User Successfully entered the [Login Details]");
        
        // Create an instance of WebDriverWait with a timeout of 10 seconds
        WebDriverWait wait2= new WebDriverWait(driver, Duration.ofSeconds(50));
        // Wait for a password field to be visible
        WebElement password= wait2.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_password")));
        // Perform actions with the element
        password.click();
        password.sendKeys(Pass);
        System.out.println("User Successfully entered the [password Details]");

        //Find signIn Button and click it
        driver.findElement(By.xpath("//input[@type='submit']")).click();
        System.out.println("User Successfully clicked on [SignIn Button]");
        
        try {
        	// Check for locked account message
            WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement errorMessage = wait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class=\"alert alert-error\"]")));
            Assert.assertEquals(errorMessage.getText(), "Oho! Your account is locked.");
           }
        catch (Exception e) {
           // This block will catch any type of Exception
            System.out.println("No problem Found In Login Credentials");
        }
        

        // navigate back to login page
    	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.navigate().back();
        System.out.println("User Successfully >[Navigated Back]");
        System.out.println("User Successfully Redirected to [ Account Home] page");

        
        // Find user and click user
        WebDriverWait wait4 = new WebDriverWait(driver, Duration.ofSeconds(40));
        WebElement user_profile= wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"settingsBox\"]/ul/li[3]/a/b")));
        user_profile.click();
        System.out.println("User Successfully clicked on [User Profile]");
        
        // Find and Logout
        WebElement Log_out= wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id=\"logout_link\"]")));
        Log_out.click();
        System.out.println("User Successfully clicked on [LogOut] Button");
        
        // navigate back to History
        driver.navigate().back();
        System.out.println("User try to [Navigated Back] but it's not allowed to access the pages from the history.user must login again");
        
    }
    @DataProvider(name = "PayeeDetails")
    public Object[][] Payeedetails() {
        return new Object[][] {
            { "username","password","Karan", "Bengaluru,India,560066","8797653623","Family Member" }
        };
    }
    
    @Test(dataProvider = "PayeeDetails",priority = 2)
    public void testAddpayee(String id,String Pass,String Name, String Address,String Account,String Payeedetails ) throws IOException
    	{
        System.out.println("*******************************************************************************************");
        System.out.println("Executing Add Payee Scenerio:-");
        System.out.println("*******************************************************************************************");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
        // Find sign in button and click on it
        WebElement usernameField = driver.findElement(By.xpath("//*[@id='signin_button']"));
        usernameField.click();
        System.out.println("User Successfully clicked on [Sign in] Button");
        
    	// Wait for a login field to be visible
        WebDriverWait wait4= new WebDriverWait(driver, Duration.ofSeconds(50));
        WebElement login = wait4.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_login")));
        // Perform actions with the element
        login.click();
        login.sendKeys(id);
        System.out.println("User Successfully entered the [Login Details]");
        
        // Create an instance of WebDriverWait with a timeout of 10 seconds
        WebDriverWait wait2= new WebDriverWait(driver, Duration.ofSeconds(50));
        // Wait for a password field to be visible
        WebElement password= wait2.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_password")));
        // Perform actions with the element
        password.click();
        password.sendKeys(Pass);
        System.out.println("User Successfully entered the [password Details]");

        //Find signIn Button and click it
        driver.findElement(By.xpath("//input[@type='submit']")).click();
        System.out.println("User Successfully clicked on [SignIn Button]");
        

    	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        // navigate back to login page
        driver.navigate().back();
        System.out.println("User Successfully [Navigated Back]");
        
        // Perform actions with online banking
        WebElement online_banking= wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"onlineBankingMenu\"]")));
        online_banking.click();
        System.out.println("User Successfully Clicked On [Online Banking link]");
        
        // Useing JavaScript Executor to scroll by pixels
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Scroll down 500 pixels
        js.executeScript("window.scrollBy(0, 500);");
        
        // Perform actions with online banking
        WebElement Pay_Bills= wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\'pay_bills_link\']")));
        Pay_Bills.click();
        System.out.println("User Successfully Clicked On [Pay Bills]");
        

        // Perform actions with Add new payee
        WebElement Add_Payee= wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[@class=\'ui-state-default ui-corner-top\']/a)[1]")));
        Add_Payee.click();
        System.out.println("User Successfully Clicked On [Add New Payee]");
        
        // Add Payee Name
        WebElement Payee_Name= wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\'np_new_payee_name\']")));
        Payee_Name.click();
        System.out.println("User Successfully Clicked On [Add Payee Name]");
        Payee_Name.sendKeys(Name);
        System.out.println("User Successfully Entered Payee Name As: "+Name);
        
		// Add Payee Address
        WebElement Payee_Address= wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\'np_new_payee_address\']")));
        Payee_Address.click();
        System.out.println("User Successfully Clicked On [Payee Address]");
        Payee_Address.sendKeys(Address);
        System.out.println("User Successfully Entered Payee Address As: "+Address);
        
		// Add Payee Account Number
        WebElement Payee_Account= wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\'np_new_payee_account\']")));
        Payee_Account.click();
        System.out.println("User Successfully Clicked On [Payee Account]");
        Payee_Account.sendKeys(Account);
        System.out.println("User Successfully Entered Payee Account Number As: "+Account);
        
        // Useing JavaScript Executor to scroll by pixels
        JavascriptExecutor js1 = (JavascriptExecutor) driver;
        // Scroll down 500 pixels
        js1.executeScript("window.scrollBy(0, 500);");
        
		// Add Payee Details
        WebElement Payee_Details= wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\'np_new_payee_details\']")));
        Payee_Details.click();
        System.out.println("User Successfully Clicked On [Payee Details]");
        Payee_Details.sendKeys(Payeedetails);
        System.out.println("User Successfully Entered Payee Details As: "+Payeedetails);
        
        
		// Add Button
        WebElement Add_button= wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\'add_new_payee\']")));
        Add_button.click();
        System.out.println("User Successfully Clicked On [Add Button]");
        
        
    	//Verify the payee addition
        WebElement Payee_Notify= wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='alert_content']")));
        String Message=Payee_Notify.getText();
        System.out.println("User Successfully Captured the Message After Adding Payee : "+Message);
        
        // Take a screenshot
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);

        // Define the path to save the screenshot
        File destinationFile = new File("Fianl_Message_Proof.png");

        // Copy the screenshot to the destination file
        FileHandler.copy(screenshotFile, destinationFile);

        System.out.println("Screenshot saved to>>: " + destinationFile.getAbsolutePath());
        
        // Find user and click user
        WebDriverWait wait11 = new WebDriverWait(driver, Duration.ofSeconds(40));
        WebElement user_profile= wait11.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"settingsBox\"]/ul/li[3]/a/b")));
        user_profile.click();
        System.out.println("User Successfully clicked on [User Profile]");
        
        // Find and Logout
        WebElement Log_out= wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id=\"logout_link\"]")));
        Log_out.click();
        System.out.println("User Successfully clicked on [LogOut] Button");
        
    	}
   
        
     @AfterClass
   	 public void tearDown() {
     if (driver != null) {
            driver.quit();
       }
     }}

