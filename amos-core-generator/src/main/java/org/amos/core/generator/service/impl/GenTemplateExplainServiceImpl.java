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
package org.amos.core.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.amos.core.basic.node.TreeNodeBuilder;
import org.amos.core.basic.utils.AmosUtils;
import org.amos.core.generator.entity.GenTemplateExplain;
import org.amos.core.generator.dto.GenTemplateExplainTreeDTO2;
import org.amos.core.generator.mapper.GenTemplateExplainMapper;
import org.amos.core.generator.service.IGenTemplateExplainService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author CodeGenerator
 * @since 2021-01-19
 */
@Service
public class GenTemplateExplainServiceImpl extends ServiceImpl<GenTemplateExplainMapper, GenTemplateExplain> implements IGenTemplateExplainService {

    @Override
    public List<GenTemplateExplainTreeDTO2> selectTree(QueryWrapper queryWrapper) {
        List<GenTemplateExplain> explains = baseMapper.selectList(queryWrapper);
        return new TreeNodeBuilder((AmosUtils.listCopy(explains, GenTemplateExplainTreeDTO2.class))).buildDynamic();
    }
}
