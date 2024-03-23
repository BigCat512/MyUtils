package com.example.file.util.decompress;

/**
 * <p>
 * 解压
 * </p>
 *
 * @author author
 * @version 1.0
 * @since 2024/3/20
 */
public class DecompressionFactory {

    public static DecompressStrategy getDecompressor(String fileName) {
        var fileNameLowerCase = fileName.toLowerCase();
        if (fileNameLowerCase.endsWith(".zip")) {
            return new ZipDecompressor();
        } else if (fileNameLowerCase.endsWith(".rar")) {
            return new RarDecompressor();
        } else if (fileNameLowerCase.endsWith(".7z")) {
            return new SevenZDecompressor();
        } else {
            throw new IllegalArgumentException("Unsupported file format for " + fileName);
        }
    }

}