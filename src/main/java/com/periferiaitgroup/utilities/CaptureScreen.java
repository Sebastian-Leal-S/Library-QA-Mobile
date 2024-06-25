package com.periferiaitgroup.utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CaptureScreen {
    public static String captureScreen(WebDriver driver, File rutaCarpeta) {
        String hora = HoraSistema.currentDate("HH-mm-ss");

        // WebDriver delegatedDriver = (driver).getDelegate();
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String pathImage = rutaCarpeta + "\\" + hora + ".png";

        try {
            FileUtils.copyFile(srcFile, new File(pathImage));
        }catch (IOException e){
            System.out.println("Falla al tomar la captura de pantalla\n" + e);
        }

        return new File(pathImage).toString();
    }
}
