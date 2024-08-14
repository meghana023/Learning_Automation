package org.example;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class NseTestTata {

    public static void main(String[] args) throws InterruptedException, IOException {
        // Path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");

        // Set up Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");  // Optional: Start with maximized window
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--remote-allow-origins=*");
        // Initialize the WebDriver with ChromeDriver and options
        WebDriver driver = new ChromeDriver(options);

        Thread.sleep(500);
        // Open a website (optional)
        driver.get("https://www.screener.in/screens/566241/stock-list/");
        WebElement element = driver.findElement(By.xpath("//a[contains(text(), 'Tata Elxsi')]"));
        element.click();

// Get the handle of the current window
        String originalWindowHandle = driver.getWindowHandle();

        // Get all window handles
        Set<String> windowHandles = driver.getWindowHandles();

        // Iterate through window handles and switch to the new window
        for (String handle : windowHandles) {
            if (!handle.equals(originalWindowHandle)) {
                driver.switchTo().window(handle);
                break; // Exit loop once the new window is found and switched to
            }
        }

        // Now you can interact with the new window
        WebElement inverstor = driver.findElement(By.xpath("//a[contains(text(), 'Investors')]"));
        inverstor.click();

        // Do something with the browser (e.g., print the title of the page)
        System.out.println("Page title is: " + driver.getTitle());

       List<WebElement> rows = driver.findElements(By.xpath("//*[@id=\"quarterly-shp\"]/div/table")); // Example XPath
        //List<WebElement> rows = driver.findElements(By.xpath("//*[@id=\"quarterly-shp\"]/div/table/tbody/tr[1]"));

        // Create a new workbook and a sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheetb = workbook.createSheet("TataElxsi");

        // Iterate over rows and write data to Excel
        int rowIndex = 0;
        for (WebElement rowElement : rows) {
            Row row = sheetb.createRow(rowIndex++);
            List<WebElement> cells = rowElement.findElements(By.tagName("td"));

            int cellIndex = 0;
            for (WebElement cellElement : cells) {
                Cell cell = row.createCell(cellIndex++);
                cell.setCellValue(cellElement.getText());
            }
        }


        // Write the output to a file
        try (FileOutputStream fileOut = new FileOutputStream("webData.xlsx")) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }

        System.out.println("Data written to Excel file successfully.");

        // Close the browser
        // driver.quit();
    }

}