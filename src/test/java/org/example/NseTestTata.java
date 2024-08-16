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
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

        String homeWindow = driver.getWindowHandle();

        Workbook workbook = new XSSFWorkbook();
        List<String> stocksList = Arrays.asList("Tata Elxsi", "Page Industries", "Solar Industries");

        for(String stock : stocksList){
            WebElement element = driver.findElement(By.xpath("//a[contains(text(), '"+stock+"')]"));
            element.click();

            // Get the handle of the current window
            String originalWindowHandle = driver.getWindowHandle();

            // Get all window handles
            Set<String> windowHandles = driver.getWindowHandles();

            // Iterate through window handles and switch to the new window
            for (String handle : windowHandles) {
                if (!handle.equals(originalWindowHandle)) {
                    if(driver.switchTo().window(handle).getTitle().contains(stock)){
                        driver.switchTo().window(handle);
                        break; // Exit loop once the new window is found and switched to
                    }
                }
            }

            // Now you can interact with the new window
            WebElement inverstor = driver.findElement(By.xpath("//a[contains(text(), 'Investors')]"));
            inverstor.click();

            // Do something with the browser (e.g., print the title of the page)
            System.out.println("Page title is: " + driver.getTitle());

            WebElement rows = driver.findElement(By.id("quarterly-shp")); // Example XPath
            //List<WebElement> rows = driver.findElements(By.xpath("//*[@id=\"quarterly-shp\"]/div/table/tbody/tr[1]"));

            WebElement allColnHeader = rows.findElement(By.tagName("thead"));

            // Now get all the TR elements from the table
            List<WebElement> allRows = rows.findElements(By.tagName("tr"));

            // Create a new workbook and a sheet

            Sheet sheetb = workbook.createSheet(stock);

            // Iterate over rows and write data to Excel
            int rowIndex = 0;

            List<WebElement> colnElements = allColnHeader.findElements(By.tagName("th"));
            //for (WebElement colnElement : colnElements) {
                Row rowHeader = sheetb.createRow(rowIndex++);
                //List<WebElement> cells = colnElement.findElements(By.tagName("th"));

                int cellIndex = 0;
                for (WebElement cellElement : colnElements) {
                    Cell cell = rowHeader.createCell(cellIndex++);
                    cell.setCellValue(cellElement.getText());
                }
            //}

            for (WebElement rowElement : allRows) {
                Row row = sheetb.createRow(rowIndex++);
                List<WebElement> cells = rowElement.findElements(By.tagName("td"));

                cellIndex = 0;
                for (WebElement cellElement : cells) {
                    Cell cell = row.createCell(cellIndex++);
                    cell.setCellValue(cellElement.getText());
                }
                if(cells.size()>0) {
                    System.out.println(cells.get(cells.size() - 1).getText());
                }
            }
            TimeUnit.SECONDS.sleep(5);
            driver.switchTo().window(homeWindow);
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