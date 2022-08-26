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
package org.amos.core.generator.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.amos.core.generator.entity.GenTemplateExplain;
import org.amos.core.generator.dto.GenTemplateExplainTreeDTO2;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author CodeGenerator
 * @since 2021-01-19
 */
public interface IGenTemplateExplainService extends IService<GenTemplateExplain> {
    /**
     * 查询所有配置信息(树形)
     * @return
     */
    List<GenTemplateExplainTreeDTO2> selectTree(QueryWrapper queryWrapper);
}
