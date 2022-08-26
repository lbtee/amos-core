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
package org.amos.core.quartz.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.amos.core.basic.base.BaseEntity;

/**
 * <p>
 * 定时任务
 * </p>
 *
 * @author CodeGenerator
 * @since 2021-01-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("amos_quartz_job")
@ApiModel(value="QuartzJob对象", description="定时任务")
public class QuartzJob extends BaseEntity {


    @ApiModelProperty(value = "任务类名称")
    private String beanName;

    @ApiModelProperty(value = "cron 表达式")
    private String cronExpression;

    @ApiModelProperty(value = "任务名称")
    private String jobName;

    @ApiModelProperty(value = "方法名称")
    private String methodName;

    @ApiModelProperty(value = "参数")
    private String params;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态 0正常 -1停止")
    private Integer status;


}
