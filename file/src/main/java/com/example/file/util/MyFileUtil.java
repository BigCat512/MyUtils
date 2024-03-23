package com.example.file.util;

import cn.hutool.core.util.IdUtil;
import lombok.SneakyThrows;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * <p>
 * 文件工具类
 * </p>
 *
 * @author author
 * @version 1.0
 * @since 2024/3/20
 */

public class MyFileUtil {


    /**
     * 去掉后缀
     *
     * @param fileName {@link String}
     * @return {@link String}
     * @author author
     * @since 2024/3/20
     **/
    public static String getNotSuffix(String fileName) {
        var lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            return fileName.substring(0, lastDotIndex);
        }
        return fileName;
    }

    /**
     * 获取后缀
     *
     * @param fileName {@link String}
     * @return {@link String}
     * @author author
     * @since 2024/3/20
     **/
    public static String getSuffix(String fileName) {
        var lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            return fileName.substring(lastDotIndex);
        }
        return fileName;
    }

    /**
     * 获取临时目录
     *
     * @return {@link File}
     * @author author
     * @since 2024/3/20
     **/
    public static File getTempFile(String dir) {
        var path = Paths.get(System.getProperty("user.dir"), "temp", dir, String.valueOf(IdUtil.getSnowflakeNextId()));
        var tempFile = path.toFile();
        if (!tempFile.exists()) tempFile.mkdirs();
        return tempFile;
    }

    /**
     * 转换
     *
     * @param file {@link MultipartFile}
     * @return {@link File}
     * @author author
     * @since 2024/3/20
     **/
    public static File multiPartToFile(MultipartFile file) {
        File convFile;
        try {
            var path = Paths.get(System.getProperty("user.dir"), "temp", file.getOriginalFilename());
            convFile = path.toFile();
            file.transferTo(path);
            return convFile;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets multipart file.
     *
     * @param file the file
     * @return the multipart file
     */
    public static MultipartFile fileToMultiPart(File file) {
        var item = new DiskFileItemFactory().createItem("file"
                , MediaType.MULTIPART_FORM_DATA_VALUE
                , true
                , file.getName());
        try (var input = Files.newInputStream(file.toPath()); var os = item.getOutputStream()) {
            IOUtils.copy(input, os);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid file: " + e, e);
        }

        return new CommonsMultipartFile(item);
    }

    /**
     * 删除指定目录及其包含的所有文件和子目录。
     *
     * @param directory 要删除的目录
     */
    @SneakyThrows
    public static void delDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        MyFileUtil.delDirectory(file);
                    } else {
                        if (!file.delete()) {
                            throw new IOException("Failed to delete file: " + file.getAbsolutePath());
                        }
                    }
                }
            }
            if (!directory.delete()) {
                throw new IOException("Failed to delete directory: " + directory.getAbsolutePath());
            }
        }
    }

}
