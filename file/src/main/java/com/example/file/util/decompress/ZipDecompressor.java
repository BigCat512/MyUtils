package com.example.file.util.decompress;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.poi.util.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;


/**
 * <p>
 * zip 解压
 * </p>
 *
 * @author author
 * @version 1.0
 * @since 2024/3/20
 */
@Slf4j
public class ZipDecompressor implements DecompressStrategy {

    public void decompress(MultipartFile uploadFile, File destDir) {
        try (var is = uploadFile.getInputStream(); var zipInput = new ZipArchiveInputStream(is, "GBK", Boolean.TRUE)) {
            ZipArchiveEntry entry;
            while ((entry = zipInput.getNextZipEntry()) != null) {
                var entryFile = new File(destDir, entry.getName());
                entryFile.getParentFile().mkdirs();
                if (!entry.getName().contains(".")) {
                    entryFile.mkdir();
                } else {
                    try (var outputStream = new FileOutputStream(entryFile)) {
                        IOUtils.copy(zipInput, outputStream);
                    }
                }
            }
        } catch (Exception e) {
            log.error("unzip error：{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // =========================================== private method ===========================================


}