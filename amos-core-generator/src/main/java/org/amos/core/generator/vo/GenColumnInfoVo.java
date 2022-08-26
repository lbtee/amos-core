package org.amos.core.generator.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "代码生成字段信息配置")
public class GenColumnInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "表名")
    private String tableName;

    @ApiModelProperty(value = "数据库字段名称")
    private String columnName;

    @ApiModelProperty(value = "数据库字段类型")
    private String columnType;

    @ApiModelProperty(value = "实体类属性名称")
    private String fieldName;

    @ApiModelProperty(value = "实体类属性类型")
    private Integer fieldType;

    @ApiModelProperty(value = "实体类属性备注")
    private String comment;

    @ApiModelProperty(value = "是否必填（0:是,1:否）")
    private Integer isRequired;

    @ApiModelProperty(value = "是否为插入字段（0:是,1:否）")
    private Integer isInsert;

    @ApiModelProperty(value = "是否编辑字段（0:是,1:否）")
    private Integer isEdit;

    @ApiModelProperty(value = "是否列表字段（0:是,1:否）")
    private Integer isList;

    @ApiModelProperty(value = "是否查询字段（0:是,1:否）")
    private Integer isQuery;

    @ApiModelProperty(value = "EQ等于、NE不等于、GT大于、LT小于、LIKE模糊、BETWEEN范围")
    private String queryType;

    @ApiModelProperty(value = "html组件类型")
    private String htmlType;

    @ApiModelProperty(value = "字典类型")
    private String dictName;

    @ApiModelProperty(value = "排序")
    private Integer sort;


}
