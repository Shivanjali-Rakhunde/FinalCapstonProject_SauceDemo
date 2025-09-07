package com.saucedemo.utils; 

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ScreenshotUtil {

    public static String takeScreenshot(WebDriver driver, String testName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String destPath = "test-output/screenshot/" + testName + ".png";
        try {
            File destFile = new File(destPath);
            destFile.getParentFile().mkdirs();  // create folders if needed
            FileUtils.copyFile(srcFile, destFile);
            System.out.println("Screenshot saved at: " + destFile.getAbsolutePath());
            return destFile.getAbsolutePath();  // <-- return the full path here
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
            return null;  // return null if saving failed
        }
    }
}
