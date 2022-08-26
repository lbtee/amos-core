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

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.amos.core.basic.base.BaseController;
import org.amos.core.basic.constant.SystemConstant;
import org.amos.core.basic.utils.AmosUtils;
import org.amos.core.basic.vo.R;
import org.amos.core.generator.dto.GenDatasourceDTO;
import org.amos.core.generator.entity.GenDatasource;
import org.amos.core.generator.gen.*;
import org.amos.core.generator.service.IGenDatasourceService;
import org.amos.core.generator.vo.DbTypeVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 数据源配置表 前端控制器
 * </p>
 *
 * @author CodeGenerator
 * @since 2021-01-19
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/gen/datasource")
public class GenDatasourceController extends BaseController {

    private final IGenDatasourceService genDatasourceService;

    @GetMapping("/list")
    public R list(GenDatasourceDTO dto) {
        IPage<GenDatasource> list = genDatasourceService.page(initPage(dto), buildWrapper(dto));
        return R.ok(list);
    }

    @RequestMapping("/table/{id}")
    public R tableList(@PathVariable("id") Long id) {
        GenDatasource datasource = genDatasourceService.getById(id);
        GenConfig generatorConfig = GenConfig.build(datasource);
        SQLService service = SQLServiceFactory.build(generatorConfig);
        List<TableDefinition> list = service.getTableSelector(generatorConfig).getSimpleTableDefinitions();
        return R.ok(list);
    }

    @PostMapping("/connection/test")
    public R connectionTest(@RequestBody GenDatasourceDTO dto) {
        String error = DBConnect.testConnection(GenConfig.build(AmosUtils.copy(dto, GenDatasource.class)));
        if (ObjectUtil.isNotEmpty(error)) {
            return R.error(error);
        }
        return R.ok();
    }

    @PostMapping("/db-type")
    public R dbType() {
        List<DbTypeVO> vos = Stream.of(DbType.values())
                .map(dbType -> new DbTypeVO(dbType.getDisplayName(), dbType.getType()))
                .collect(Collectors.toList());
        return R.ok(vos);
    }

    @PostMapping("/create")
    public R create(@RequestBody @Validated GenDatasourceDTO dto) {
        GenDatasource datasource = AmosUtils.copy(dto, GenDatasource.class);
        DbType dbType = DbType.of(datasource.getDbType());
        if (ObjectUtil.isNotEmpty(dbType)) {
            datasource.setDriverClass(dbType.getDriverClass());
        }
        genDatasourceService.save(datasource);
        return R.ok();
    }

    @PostMapping("/update")
    public R update(@RequestBody @Validated GenDatasourceDTO dto) {
        GenDatasource datasource = AmosUtils.copy(dto, GenDatasource.class);
        DbType dbType = DbType.of(datasource.getDbType());
        if (ObjectUtil.isNotEmpty(dbType)) {
            datasource.setDriverClass(dbType.getDriverClass());
        }
        genDatasourceService.updateById(datasource);
        return R.ok();
    }

    @PostMapping("/remove")
    public R remove(@RequestBody Set<Long> ids) {
        UpdateWrapper<GenDatasource> uw = new UpdateWrapper<>();
        uw.in("id", ids).set("is_deleted", SystemConstant.SYS_DELETE_FLAG_ALREADY);
        genDatasourceService.update(uw);
        return R.ok();
    }


    @GetMapping("/cache-list")
    public R cacheList(GenDatasourceDTO dto) {
        List<GenDatasource> list = genDatasourceService.list(buildWrapper(dto));
        return R.ok(list);
    }
}
