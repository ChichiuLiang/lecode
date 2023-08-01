package org.example.util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

public class ImageGenerator {
    public static byte[] generateImageFromUrl(String url, int width, int height) throws IOException {
        // 创建 HtmlUnit 驱动
        WebDriver driver = new HtmlUnitDriver();

        // 打开网页
        driver.get(url);

        // 调整浏览器窗口大小
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(width, height));

        // 获取屏幕截图
        BufferedImage image = null;
        try {
            image = ImageIO.read(new URL(driver.getCurrentUrl()));
        } catch (Exception e) {
            System.out.println("无法从 URL 加载图像: " + e.getMessage());
            return null;
        }

        if (image == null) {
            System.out.println("加载的图像为空！");
            return null;
        }

        // 将 BufferedImage 转换为字节数组
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            if (!ImageIO.write(image, "png", outputStream)) {
                throw new IOException("无法将图像转换为字节数组");
            }
        } catch (IOException e) {
            System.out.println("无法将图像转换为字节数组: " + e.getMessage());
            return null;
        }

        driver.quit();

        return outputStream.toByteArray();
    }
}
