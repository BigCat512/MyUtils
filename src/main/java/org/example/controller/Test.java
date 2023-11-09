package org.example.controller;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * $
 * </p>
 *
 * @author author
 * @version 1.0
 * @since 2023/10/24
 */
@Validated
@RequestMapping("/test")
@RestController
public class Test {

    @PostMapping(value = "/valid")
    public void updateBank(@RequestBody @NotEmpty(message = "参数不能为空") List<String> banks) {
        System.out.println(banks);
    }

    @GetMapping(value = "/test/file")
    public String testFile() {
        String destination = "/usr/local/app/测试文件创建.txt"; // 指定目标文件夹
        try {
            Path destinationPath = Paths.get(destination);
            Files.createDirectories(destinationPath.getParent()); // 创建目标文件夹
            List<String> list = new ArrayList<>();
            list.add("this is title");
            list.add("this is content");
            Files.write(destinationPath, list, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "成功";
    }
}
