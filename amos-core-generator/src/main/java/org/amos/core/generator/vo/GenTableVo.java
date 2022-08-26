package org.amos.core.generator.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "代码生成数据表信息配置")
public class GenTableVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "方案名称")
    private String name;

    @ApiModelProperty(value = "数据源ID")
    private Long dataSourceId;

    @ApiModelProperty(value = "模板组ID")
    private Long groupId;

    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "表名前缀")
    private String tablePrefix;

    @ApiModelProperty(value = "类名")
    private String className;

    @ApiModelProperty(value = "包名")
    private String packageName;

    @ApiModelProperty(value = "生成路径")
    private String path;

    @ApiModelProperty(value = "模块名称")
    private String moduleName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
}
