package org.amos.core.generator.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.amos.core.basic.base.BaseEntity;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("amos_gen_column_info")
@ApiModel(value = "代码生成字段信息配置")
public class AmosGenColumnInfo extends BaseEntity {

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
    private Integer sort;


}
