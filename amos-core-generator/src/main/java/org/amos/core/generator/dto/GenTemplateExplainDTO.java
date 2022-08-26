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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.amos.core.basic.annotation.AmosQuery;
import org.amos.core.basic.base.BaseDTO;
import org.amos.core.basic.enums.SqlKeyWord;

/**
* <p>
    * 模板表达式说明
    * </p>
*
* @author CodeGenerator
* @since 2021-01-19
*/
@Data
@ApiModel(value="GenTemplateExplain对象", description="模板表达式说明")
public class GenTemplateExplainDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父ID")
    private Long pid;

    @ApiModelProperty(value = "表达式")
    @AmosQuery(column = "expression", keyword = SqlKeyWord.LIKE)
    private String expression;

    @ApiModelProperty(value = "描述")
    @AmosQuery(column = "text", keyword = SqlKeyWord.LIKE)
    private String text;

    private Integer type;



}
