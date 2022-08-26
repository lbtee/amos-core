package org.amos.core.generator.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.amos.core.basic.base.BaseController;
import org.amos.core.basic.enums.JavaFieldType;
import org.amos.core.basic.enums.JavaType;
import org.amos.core.basic.utils.AmosUtils;
import org.amos.core.basic.vo.R;
import org.amos.core.generator.dto.GenCodeDTO;
import org.amos.core.generator.dto.GenSettingDTO;
import org.amos.core.generator.dto.GenTableDTO;
import org.amos.core.generator.entity.AmosGenTableInfo;
import org.amos.core.generator.service.GenService;
import org.amos.core.generator.service.GenTableService;
import org.amos.core.generator.vo.GenColumnVo;
import org.amos.core.generator.vo.GenSettingVo;
import org.amos.core.generator.vo.GenTableVo;
import org.amos.core.generator.vo.GenValidVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gen")
public class GenController extends BaseController {

    private final GenService genService;
    private final GenTableService genTableService;

    @GetMapping("/table-list")
    public R tableList(GenTableDTO dto){
        IPage<AmosGenTableInfo> page = genService.getGenTablePage(new Page<>(), buildWrapper(dto));
        IPage<GenTableVo> res = AmosUtils.pageCopy(page, GenTableVo.class);
        return R.ok(res);
    }

    @GetMapping("/column/set-info")
    public R columnList(@NotBlank(message = "缺少参数") String tableName){
        GenSettingVo vo = new GenSettingVo();
        List<GenColumnVo> columnVos = genService.getGenColumnList(tableName);
        if (ObjectUtil.isEmpty(columnVos)){
            columnVos = genService.getGenColDefaultList(tableName);
        }

        List<GenValidVo> validVos = genService.getGenColumnValidList(tableName);
        if (ObjectUtil.isEmpty(validVos)){
            validVos = genService.getGenCoValidDefaultlList(tableName);
        }

        GenTableVo tableInfo = genService.getGenTableInfo(tableName);
        if (ObjectUtil.isEmpty(tableInfo)){
            tableInfo = genService.getGenTableDefaultInfo(tableName);
        }
        vo.setColumnVos(columnVos);
        vo.setValidVos(validVos);
        vo.setTableVo(tableInfo);
        return R.ok(vo);
    }

    @GetMapping("/dict/java-type")
    public R dictJavaType(){
        List<JavaType> dictList = JavaFieldType.dictList();
        return R.ok(dictList);
    }

    @PostMapping("/setting/save")
    @ApiOperation(value = "保存代码生成配置")
    public R create(@RequestBody @Validated GenSettingDTO dto) {
        genService.saveGenSetting(dto);
        return R.ok();
    }

    @PostMapping("/setting/refresh")
    @ApiOperation(value = "保存代码生成配置")
    public R refresh(@RequestBody @Validated GenSettingDTO dto) {
        genService.saveGenSetting(dto);
        return R.ok();
    }

    @PostMapping("/table/save")
    public R saveTableInfo(@RequestBody GenTableDTO dto){
        AmosGenTableInfo info = AmosUtils.copy(dto, AmosGenTableInfo.class);
        genTableService.saveOrUpdate(info);
        return R.ok();
    }

    @PostMapping("/generator")
    @ApiOperation(value = "生成代码")
    public R generator(@RequestBody @Validated GenCodeDTO dto) {
        genService.genCode(dto.getId());
        return R.ok();
    }

    @PostMapping("/preview")
    @ApiOperation(value = "生成代码")
    public R preview(@RequestBody @Validated GenCodeDTO dto) {
        Object o = genService.previewCode(dto.getId());
        return R.ok(o);
    }

    @GetMapping("/download")
    @ApiOperation(value = "下载代码")
    public R download(GenCodeDTO dto, HttpServletResponse response) {
        genService.downloadCode(dto.getId(), response);
        return R.ok();
    }
}
