package org.example;
import org.example.util.ImageGenerator;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;

public class ImageMain {
    public static void main(String[] args) {
        try {
            String url = "https://zhuanlan.zhihu.com/p/133479240";
            int width = 800;
            int height = 600;

            byte[] imageBytes = ImageGenerator.generateImageFromUrl(url, width, height);

            // 保存图片文件
            FileOutputStream fos = new FileOutputStream(new File("output.png"));
            FileCopyUtils.copy(imageBytes, fos);
            fos.close();

            System.out.println("图片生成成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
