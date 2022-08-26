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
package org.amos.core.generator.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import org.amos.core.basic.base.BaseController;
import org.amos.core.basic.constant.SystemConstant;
import org.amos.core.basic.utils.AmosUtils;
import org.amos.core.basic.vo.R;
import org.amos.core.generator.entity.GenTemplateExplain;
import org.amos.core.generator.dto.GenTemplateExplainDTO;
import org.amos.core.generator.dto.GenTemplateExplainTreeDTO2;
import org.amos.core.generator.service.IGenTemplateExplainService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author CodeGenerator
 * @since 2021-01-19
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/gen/template-explain")
public class GenTemplateExplainController extends BaseController {
    
    private final IGenTemplateExplainService genTemplateExplainService;
    
    @GetMapping("/list")
    public R list(GenTemplateExplainDTO dto) {
        List<GenTemplateExplainTreeDTO2> list = genTemplateExplainService.selectTree(buildWrapper(dto));
        return R.ok(list);
    }

    @PostMapping("/create")
    public R create(@RequestBody @Validated GenTemplateExplainDTO dto) {
        GenTemplateExplain explain = AmosUtils.copy(dto, GenTemplateExplain.class);
        genTemplateExplainService.save(explain);
        return R.ok();
    }

    @PostMapping("/update")
    public R update(@RequestBody @Validated GenTemplateExplainDTO dto) {
        GenTemplateExplain explain = AmosUtils.copy(dto, GenTemplateExplain.class);
        genTemplateExplainService.updateById(explain);
        return R.ok();
    }

    @PostMapping("/remove")
    public R remove(@RequestBody Set<Long> ids) {
        UpdateWrapper<GenTemplateExplain> uw = new UpdateWrapper<>();
        uw.in("id", ids).set("is_deleted", SystemConstant.SYS_DELETE_FLAG_ALREADY);
        genTemplateExplainService.update(uw);
        return R.ok();
    }
}
