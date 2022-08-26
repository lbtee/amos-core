/**
*    Copyright (c) 2020-2030 LiuBoTao [1211265557@qq.com]
*
*    Licensed under the Apache License, Version 2.0 (the "License");
*    you may not use this file except in compliance with the License.
*    You may obtain a copy of the License at
*
*        http://www.apache.org/licenses/LICENSE-2.0
*
*    Unless required by applicable law or agreed to in writing, software
*    distributed under the License is distributed on an "AS IS" BASIS,
*    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*    See the License for the specific language governing permissions and
*    limitations under the License.
*/
package org.amos.core.generator.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 代码生成字段信息配置
 * </p>
 *
 * @author CodeGenerator
 * @since 2021-01-17
 */
@Data
public class GenValidDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "表名称")
    private String tableName;

    @ApiModelProperty(value = "列名称")
    private String columnName;

    @ApiModelProperty(value = "列类型")
    private String columnType;

    @ApiModelProperty(value = "校验提示")
    private String validPrompt;

    @ApiModelProperty(value = "实体类备注")
    private String comment;

    @ApiModelProperty(value = "是否必填（0:是,1:否）")
    private Boolean isNotNull;

    @ApiModelProperty(value = "校验规则")
    private String validType;

}
