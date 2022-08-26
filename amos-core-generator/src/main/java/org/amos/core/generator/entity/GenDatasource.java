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
package org.amos.core.generator.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.amos.core.basic.base.BaseEntity;

/**
 * <p>
 * 数据源配置表
 * </p>
 *
 * @author CodeGenerator
 * @since 2021-01-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("amos_gen_datasource")
@ApiModel(value="GenDatasource对象", description="数据源配置表")
public class GenDatasource extends BaseEntity {

    @ApiModelProperty(value = "数据源名称")
    private String name;

    @ApiModelProperty(value = "数据库类型，1：MySql, 2:Oracle, 3:sqlserver")
    private Integer dbType;

    @ApiModelProperty(value = "数据库驱动")
    private String driverClass;

    @ApiModelProperty(value = "数据库名称")
    private String dbName;

    @ApiModelProperty(value = "数据库host")
    private String host;

    @ApiModelProperty(value = "数据库端口")
    private Integer port;

    @ApiModelProperty(value = "数据库用户名")
    private String username;

    @ApiModelProperty(value = "数据库密码")
    private String password;

    private String packageName;

    private String delPrefix;

    private Integer groupId;


}
