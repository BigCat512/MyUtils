package org.example.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * <p>
 * 基础实体类，当前实体类封装一些数据库表结构公共字段，如果要使用当前类必须遵循该表结构的定义规范。
 * 字段规范必须一致，如果不遵循当前规范设计的表结构尽量不要使用该类的定义，避免出现不可控制的错误
 * <br>
 * 规范如下：
 * <pre>
 *   DROP TABLE IF EXISTS `base`;
 *   CREATE TABLE `base`
 *   (
 *   `id`            bigint(20)                      NOT NULL DEFAULT 0 COMMENT '主键 雪花算法',
 *   `code`          varchar(20)                  NOT NULL DEFAULT '' COMMENT '编码',
 *   `name`          varchar(50)                  NOT NULL DEFAULT '' COMMENT '名称',
 *   `creator_code`  varchar(20)                  NOT NULL DEFAULT '' COMMENT '创建人编码',
 *   `creator_name`  varchar(50)                  NOT NULL DEFAULT '' COMMENT '创建人名称',
 *   `create_time`   datetime                     NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
 *   `modifier_code` varchar(20)                  NOT NULL DEFAULT '' COMMENT '最后修改人编码',
 *   `modifier_name` varchar(50)                  NOT NULL DEFAULT '' COMMENT '最后修改人名称',
 *   `modify_time`   datetime                              DEFAULT NULL COMMENT '最后修改时间',
 *   `del_flag`      tinyint(1) unsigned          NOT NULL DEFAULT 0 COMMENT ' 删除标识 0未删除 1删除 ',
 *   `state`         tinyint(1) unsigned zerofill NOT NULL DEFAULT 0 COMMENT ' 业务状态具体视业务场景需求，在系统建立常量 ',
 *   `remark`        text                         NOT NULL DEFAULT '' COMMENT ' 业务备注 ',
 *   PRIMARY KEY (`id`)
 *   ) ENGINE = InnoDB
 *   DEFAULT CHARSET = utf8mb4
 *   COLLATE = utf8mb4_german2_ci COMMENT ='表结构公共字段';
 * </pre>
 * </p>
 *
 * @author ZCM
 * @since 2021/12/28
 */
@Data
@Accessors(chain = true)
public abstract class BaseEntity implements Serializable {

    /**
     * 主键
     * <p>
     * 默认配置主键生成策略为（ASSIGN_ID），MyBatis Plus使用雪花算法生成ID，
     * 由于ID位数过长导致前端数据精度丢失, 在json序列化的时候将ID转换成String类型返回给前端
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 编码
     */
    @TableField(fill = FieldFill.INSERT)
    private String code;

    /**
     * 创建人用户ID
     */
    @TableField(fill = FieldFill.INSERT)
    private String creatorCode;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String creatorName;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * "修改人用户ID"
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String modifierCode;

    /**
     * 修改人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String modifierName;

    /**
     * "修改时间"
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifyTime;


    /**
     * "是否已删除 0 未删除 1 已删除"
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer delFlag;

}
