package org.example;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * <p>
 *
 * </p>
 *
 * @author XJH
 * @version 1.0
 * @since 2023/5/19
 */
public class BaseCodeGenerator {
    /**
     * 数据库地址
     */
    private static final String URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&useSSL=false&characterEncoding=utf8";
    /**
     * 驱动名称
     */
    private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
    /**
     * 数据库用户名
     */
    private static final String USERNAME = "root";
    /**
     * 数据库密码
     */
    private static final String PASSWORD = "123456";
    /**
     * 作者
     */
    private static final String AUTHOR = "Xjh";
    // 表名，多个英文逗号分割
    private static final String TABLE_NAME = "user";


    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir") + "/src/main/java";
        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setUrl(URL)
                .setDriverName(DRIVER_NAME)
                .setUsername(USERNAME)
                .setPassword(PASSWORD);
        // 代码生成器
        new AutoGenerator()
                // 全局配置
                .setGlobalConfig(new GlobalConfig()
                        .setOutputDir(projectPath)
                        .setAuthor(AUTHOR)
                        .setOpen(Boolean.FALSE)
                        .setFileOverride(Boolean.TRUE)
                        .setIdType(IdType.NONE)
                        .setServiceName("%sService")
                )
                // 数据源配置
                .setDataSource(dataSourceConfig)
                // 包配置
                .setPackageInfo(new PackageConfig()
                        .setParent("org.example.dao")
                        .setEntity("entity")
                        .setService("service")
                        .setServiceImpl("service.impl")
                        .setMapper("mapper")
                )
                // 配置模板
                .setTemplate(new TemplateConfig()
                        .setController(null)
                        .setService("self_templates/service.java")
                        .setServiceImpl("self_templates/serviceImpl.java")
                        .setXml(null)
                )
                // 策略配置
                .setStrategy(new StrategyConfig()
                                .setSuperMapperClass("org.example.common.config.CommonMapper")
                                .setNaming(NamingStrategy.underline_to_camel)
                                .setColumnNaming(NamingStrategy.underline_to_camel)
                                .setEntityLombokModel(true)
                                .setInclude(TABLE_NAME.split(","))
                                .setControllerMappingHyphenStyle(true)
                                .setEntityTableFieldAnnotationEnable(Boolean.TRUE)
                )
                .setTemplateEngine(new FreemarkerTemplateEngine())
                .execute();

        // DTO 代码生成器
        new AutoGenerator()
                // 全局配置
                .setGlobalConfig(new GlobalConfig()
                                .setOutputDir(projectPath)
                                .setAuthor(AUTHOR)
                                .setOpen(Boolean.FALSE)
                                .setFileOverride(true)
                                .setEntityName("%sDTO")
                )
                // 数据源配置
                .setDataSource(dataSourceConfig)
                // 包配置
                .setPackageInfo(new PackageConfig()
                                .setParent("org.example.domain")
                                .setEntity("dto")
                )
                // 配置模板
                .setTemplate(new TemplateConfig()
                                .setEntity("self_templates/dto.java")
                                .setController(null)
                                .setService(null)
                                .setServiceImpl(null)
                                .setMapper(null)
                                .setXml(null)
                )
                // 策略配置
                .setStrategy(new StrategyConfig()
                                .setNaming(NamingStrategy.underline_to_camel)
                                .setColumnNaming(NamingStrategy.underline_to_camel)
                                .setInclude(TABLE_NAME.split(","))
                                .setControllerMappingHyphenStyle(true)
                )
                .setTemplateEngine(new FreemarkerTemplateEngine())
                .execute();


    }
}
