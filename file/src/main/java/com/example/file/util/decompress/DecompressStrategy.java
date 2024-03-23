package com.example.file.util.decompress;

import com.example.file.util.MyFileUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.function.Function;

/**
 * <p>
 * 解压
 * </p>
 *
 * @author author
 * @version 1.0
 * @since 2024/3/20
 */
public interface DecompressStrategy {

    /**
     * 解压
     *
     * @param uploadFile {@link MultipartFile}
     * @param function   {@link Function<File, R>}
     * @return {@link R}
     * @author author
     * @since 2024/3/20
     **/
    default <R> R decompress(MultipartFile uploadFile, Function<File, R> function) {
        var targetFile = MyFileUtil.getTempFile(MyFileUtil.getNotSuffix(uploadFile.getOriginalFilename()));
        try {
            this.decompress(uploadFile, targetFile);
            return function.apply(targetFile);
        } catch (Exception e) {
            throw new RuntimeException("decompress error：" + e.getMessage());
        } finally {
            MyFileUtil.delDirectory(targetFile);
        }
    }

    /**
     * 解压
     *
     * @param uploadFile {@link MultipartFile}
     * @param targetFile {@link File}
     * @author author
     * @since 2024/3/20
     **/
    void decompress(MultipartFile uploadFile, File targetFile);

}