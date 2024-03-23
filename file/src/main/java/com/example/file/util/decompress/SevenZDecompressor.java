package com.example.file.util.decompress;

import com.example.file.util.MyFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.nio.file.Files;

/**
 * <p>
 * 7z 解压
 * </p>
 *
 * @author author
 * @version 1.0
 * @since 2024/3/20
 */
@Slf4j
public class SevenZDecompressor implements DecompressStrategy {
    @Override
    public void decompress(MultipartFile uploadFile, File targetFile) {
        var srcFile = MyFileUtil.multiPartToFile(uploadFile);
        SevenZFile zIn = null;
        try {
            zIn = new SevenZFile(srcFile);
            SevenZArchiveEntry entry = null;
            File newFile = null;
            while ((entry = zIn.getNextEntry()) != null) {
                // 不是文件夹就进行解压
                if (!entry.isDirectory()) {
                    newFile = new File(targetFile, entry.getName());
                    var out = Files.newOutputStream(newFile.toPath());
                    var bos = new BufferedOutputStream(out);
                    var len = -1;
                    var buf = new byte[(int) entry.getSize()];
                    while ((len = zIn.read(buf)) != -1) {
                        bos.write(buf, 0, len);
                    }
                    bos.flush();
                    bos.close();
                    out.close();
                }
            }
            srcFile.delete();
        } catch (Exception e) {
            log.error("un7z error：{}", e.getMessage());
            throw new RuntimeException(e);
        }

    }
}