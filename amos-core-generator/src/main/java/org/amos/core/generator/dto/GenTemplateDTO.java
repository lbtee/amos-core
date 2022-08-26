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
import org.amos.core.basic.base.BaseDTO;

/**
* <p>
    * 模板表
    * </p>
*
* @author CodeGenerator
* @since 2021-01-21
*/
@Data
@ApiModel(value="GenTemplate对象", description="模板表")
public class GenTemplateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模板名称")
    private String name;

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "内容")
    private String content;

    private Long groupId;

    private String groupName;



}
