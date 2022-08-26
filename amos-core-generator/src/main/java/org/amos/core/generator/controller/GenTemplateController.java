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
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.amos.core.basic.base.BaseController;
import org.amos.core.basic.constant.SystemConstant;
import org.amos.core.basic.utils.AmosUtils;
import org.amos.core.basic.vo.R;
import org.amos.core.generator.entity.GenTemplate;
import org.amos.core.generator.entity.GenTemplateGroup;
import org.amos.core.generator.dto.GenTemplateDTO;
import org.amos.core.generator.dto.GenTemplateGroupDTO;
import org.amos.core.generator.service.GenTemplateGroupService;
import org.amos.core.generator.service.GenTemplateService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 模板表 前端控制器
 * </p>
 *
 * @author CodeGenerator
 * @since 2021-01-21
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/gen/template")
public class GenTemplateController extends BaseController {
    
    private final GenTemplateService genTemplateService;
    private final GenTemplateGroupService genTemplateGroupService;

    @GetMapping("/list")
    public R list(GenTemplateDTO dto) {
        IPage<GenTemplate> list = genTemplateService.page(initPage(dto), buildWrapper(dto));
        return R.ok(list);
    }

    @GetMapping("/info/{id}")
    public R getInfo(@PathVariable Long id) {
        GenTemplate info = genTemplateService.getById(id);
        return R.ok(info);
    }

    @GetMapping("/group/list")
    public R groupList(GenTemplateGroupDTO dto) {
        List<GenTemplateGroup> list = genTemplateGroupService.list(buildWrapper(dto));
        return R.ok(list);
    }

    @PostMapping("/create")
    public R create(@RequestBody @Validated GenTemplateDTO dto) {
        GenTemplate template = AmosUtils.copy(dto, GenTemplate.class);
        genTemplateService.save(template);
        return R.ok();
    }

    @PostMapping("/update")
    public R update(@RequestBody @Validated GenTemplateDTO dto) {
        GenTemplate datasource = AmosUtils.copy(dto, GenTemplate.class);
        genTemplateService.updateById(datasource);
        return R.ok();
    }

    @PostMapping("/remove")
    public R remove(@RequestBody Set<Long> ids) {
        UpdateWrapper<GenTemplate> uw = new UpdateWrapper<>();
        uw.in("id", ids).set("is_deleted", SystemConstant.SYS_DELETE_FLAG_ALREADY);
        genTemplateService.update(uw);
        return R.ok();
    }

    @PostMapping("/group/create")
    public R createGroup(@RequestBody @Validated GenTemplateGroupDTO dto) {
        GenTemplateGroup group = AmosUtils.copy(dto, GenTemplateGroup.class);
        genTemplateGroupService.save(group);
        return R.ok();
    }

    @PostMapping("/group/update")
    public R updateGroup(@RequestBody @Validated GenTemplateGroupDTO dto) {
        GenTemplateGroup group = AmosUtils.copy(dto, GenTemplateGroup.class);
        genTemplateGroupService.updateById(group);
        return R.ok();
    }

    @PostMapping("/group/remove")
    public R removeGroup(@RequestBody Set<Long> ids) {
        int count = genTemplateGroupService.count();
        if (count == 1){
            throw new RuntimeException("删除失败,模板组至少保留一个");
        }
        UpdateWrapper<GenTemplateGroup> uw = new UpdateWrapper<>();
        uw.in("id", ids).set("is_deleted", SystemConstant.SYS_DELETE_FLAG_ALREADY);
        genTemplateGroupService.update(uw);
        return R.ok();
    }



    @GetMapping("/cache-list")
    public R cacheList(GenTemplateDTO dto) {
        List<GenTemplate> list = genTemplateService.list(buildWrapper(dto));
        return R.ok(list);
    }

}
