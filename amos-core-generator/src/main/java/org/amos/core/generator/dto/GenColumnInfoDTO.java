package org.amos.core.generator.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@ApiModel(value = "代码生成字段信息配置条件")
public class GenColumnInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "表名")
    @NotBlank(message = "表名不能为空")
    private String tableName;

    @ApiModelProperty(value = "数据库字段名称")
    @NotBlank(message = "数据库字段名称不能为空")
    private String columnName;

    @ApiModelProperty(value = "数据库字段类型")
    @NotBlank(message = "数据库字段类型不能为空")
    private String columnType;

    @ApiModelProperty(value = "实体类属性名称")
    @NotBlank(message = "实体类属性名称不能为空")
    private String fieldName;

    @ApiModelProperty(value = "实体类属性类型")
    @NotBlank(message = "实体类属性类型不能为空")
    private Integer fieldType;

    @ApiModelProperty(value = "实体类属性备注")
    @NotBlank(message = "实体类属性备注不能为空")
    private String comment;

    @ApiModelProperty(value = "是否必填（0:是,1:否）")
    private Boolean isRequired;

    @ApiModelProperty(value = "是否为插入字段（0:是,1:否）")
    private Boolean isInsert;

    @ApiModelProperty(value = "是否编辑字段（0:是,1:否）")
    private Boolean isEdit;

    @ApiModelProperty(value = "是否列表字段（0:是,1:否）")
    private Boolean isList;

    @ApiModelProperty(value = "是否查询字段（0:是,1:否）")
    private Boolean isQuery;

    @ApiModelProperty(value = "EQ等于、NE不等于、GT大于、LT小于、LIKE模糊、BETWEEN范围")
    private String queryType;

    @ApiModelProperty(value = "html组件类型")
    private String htmlType;

    @ApiModelProperty(value = "字典类型")
    private String dictName;

    @ApiModelProperty(value = "排序")
    @NotEmpty(message = "表名不能为空")
    private Integer sort;


}
