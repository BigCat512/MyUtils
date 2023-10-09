package org.example.demo.file;

import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CopyFile {

    /**
     * 项目根路径
     */
    private static String relativelyPath = System.getProperty("user.dir");

    @SneakyThrows
    public static void main(String[] args) {

        String source = "D:\\新建文件夹\\新建 文本文档.txt"; // 指定源文件
        String destination = "D:\\新建文件夹2\\新建 文本文档.txt"; // 指定目标文件夹


        try {
            Path sourcePath = Paths.get(source);
            Path destinationPath = Paths.get(destination);

            Files.createDirectories(destinationPath.getParent()); // 创建目标文件夹
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
