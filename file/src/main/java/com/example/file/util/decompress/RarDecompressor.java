package com.example.file.util.decompress;

import com.example.file.util.MyFileUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * <p>
 * rar 解压，似乎兼容rar5
 * </p>
 *
 * @author author
 * @version 1.0
 * @since 2024/3/20
 */
@Slf4j
public class RarDecompressor implements DecompressStrategy {
    @Override
    public void decompress(MultipartFile uploadFile, File targetFile) {
        var srcFile = MyFileUtil.multiPartToFile(uploadFile);
        try {
            try {
                // 第一个参数是需要解压的压缩包路径，第二个参数参考JdkAPI文档的RandomAccessFile
                // r代表以只读的方式打开文本，也就意味着不能用write来操作文件
                var randomAccessFile = new RandomAccessFile(srcFile, "r");
                var archive = SevenZip.openInArchive(null, new RandomAccessFileInStream(randomAccessFile));
                var in = new int[archive.getNumberOfItems()];
                for (int i = 0; i < in.length; i++) {
                    in[i] = i;
                }
                archive.extract(in, false, new ExtractCallback(archive, targetFile.getPath()));
                archive.close();
                randomAccessFile.close();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            srcFile.delete();
        } catch (Exception e) {
            log.error("unrar error：{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}