package util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class GetScreenShot {
    private static WebDriver driverForCapture;
    private GetScreenShot() {
    };

    public static void setWebDriverForCapture(WebDriver driver) {
        driverForCapture = driver;
    }

    public static String capture(String screenShotName) {
        TakesScreenshot ts = (TakesScreenshot) driverForCapture;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String fileName = screenShotName + ".png";
        String dest = "test-output/" + fileName;
        File destination = new File(dest);
        try {
            FileUtils.copyFile(source, destination);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileName;
    }
}
