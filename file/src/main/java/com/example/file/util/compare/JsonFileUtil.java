package com.example.file.util.compare;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONTokener;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

/**
 * <p>
 * js 文件比对，内容格式仅限json
 * </p>
 *
 * @author xjh
 * @version 1.0
 * @since 2024/4/2
 */
@Slf4j
public class JsonFileUtil {

    /**
     * 递归最大深度限制
     */
    private static final int MAX_DEPTH = 50;

    /**
     * 比对
     *
     * @param oldPath  {@link String}
     * @param newPath  {@link String}
     * @param outPath  {@link String}
     * @param fileType {@link String}
     * @author author
     * @since 2024/4/2
     **/
    public static void compare(String oldPath, String newPath, String outPath, String fileType) {
        File[] newFiles = new File(newPath).listFiles();
        if (Objects.isNull(newFiles) || newFiles.length == 0) {
            return;
        }
        var destParentPath = Paths.get(outPath);
        if (!destParentPath.toFile().exists()) destParentPath.toFile().mkdirs();
        for (File file : newFiles) {
            if (StrUtil.isBlank(fileType) || (file.getName().lastIndexOf(".") > 0 &&
                    !file.getName().substring(file.getName().lastIndexOf(".") + 1)
                            .equalsIgnoreCase(fileType))) {
                continue;
            }
            var oldFilePath = Paths.get(oldPath, file.getName());
            var oldFile = oldFilePath.toFile();
            var destPath = Paths.get(outPath, file.getName());
            try {
                if (oldFile.exists()) {
                    var oldJson = JsonFileUtil.readJsonFile(oldFile);
                    // 读取新的JSON文件
                    var newJson = JsonFileUtil.readJsonFile(file);
                    // 比较两个JSON对象并将新的节点添加到旧的JSON对象中
                    JsonFileUtil.mergeJson(oldJson, newJson, 0);
                    try (var fileWriter = new FileWriter(destPath.toFile())) {
                        oldJson.write(fileWriter, 4, 0);
                        fileWriter.flush();
                    }
                } else {
                    var sourcePath = Paths.get(file.getPath());
                    Files.copy(sourcePath, destPath, StandardCopyOption.COPY_ATTRIBUTES);
                }
            } catch (Exception e) {
                log.error("JsonFileUtil.compare error：{}", e.getMessage());
            }

        }

    }

    /**
     * 读取json
     *
     * @param file {@link String}
     * @return {@link JSONObject}
     * @author author
     * @since 2024/4/2
     **/
    private static JSONObject readJsonFile(File file) throws IOException, JSONException {
        var inputStream = new FileInputStream(file);
        var tokener = new JSONTokener(inputStream, new JSONConfig());
        return new JSONObject(tokener);
    }


    /**
     * 比对
     *
     * @param oldJson {@link JSONObject}
     * @param newJson {@link JSONObject}
     * @author author
     * @since 2024/4/2
     **/
    private static void mergeJson(JSONObject oldJson, JSONObject newJson, int depth) {
        if (depth >= MAX_DEPTH) return;
        for (String key : newJson.keySet()) {
            if (!oldJson.containsKey(key)) {
                // 如果旧的JSON对象中不包含新的节点，则添加新的节点
                oldJson.set(key, newJson.get(key));
                log.debug("JsonFileUtil.mergeJson newkey：{}, value: {}", key, newJson.get(key));
            } else {
                // 如果新的节点是JSONObject类型，则递归进行合并
                if (newJson.get(key) instanceof JSONObject && oldJson.get(key) instanceof JSONObject) {
                    JsonFileUtil.mergeJson(oldJson.getJSONObject(key), newJson.getJSONObject(key), depth + 1);
                }
            }
        }
    }

}
