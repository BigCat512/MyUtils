package com.example.file.util.decompress;

import lombok.extern.slf4j.Slf4j;
import net.sf.sevenzipjbinding.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@Slf4j
public class ExtractCallback implements IArchiveExtractCallback {

    private int index;
    private IInArchive inArchive;
    private String ourDir;

    public ExtractCallback(IInArchive inArchive, String ourDir) {
        this.inArchive = inArchive;
        this.ourDir = ourDir;
    }

    @Override
    public void setCompleted(long arg0) {
    }

    @Override
    public void setTotal(long arg0) {
    }

    @Override
    public ISequentialOutStream getStream(int index, ExtractAskMode extractAskMode) throws SevenZipException {
        this.index = index;
        final String path = (String) inArchive.getProperty(index, PropID.PATH);
        final boolean isFolder = (boolean) inArchive.getProperty(index, PropID.IS_FOLDER);
        return data -> {
            try {
                if (!isFolder) {
                    File file = new File(ourDir, path);
                    ExtractCallback.saveFile(file, data);
                }
            } catch (Exception e) {
                log.error("ExtractCallback error：{}", e.getMessage());
            }
            return data.length;
        };
    }

    @Override
    public void prepareOperation(ExtractAskMode arg0) {
    }

    @Override
    public void setOperationResult(ExtractOperationResult extractOperationResult) {

    }

    // =========================================== private method ===========================================

    /**
     * 保存文件
     *
     * @param file {@link File}
     * @param msg  {@link byte}
     * @author author
     * @since 2024/3/22
     **/
    private static void saveFile(File file, byte[] msg) {
        try (FileOutputStream fos = new FileOutputStream(file, true)) {
            fos.write(msg);
            fos.flush();
        } catch (Exception e) {
            log.error("ExtractCallback error：{}", e.getMessage());
        }
    }

}
