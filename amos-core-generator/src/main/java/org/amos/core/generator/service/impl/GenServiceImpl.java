package org.amos.core.generator.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ZipUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.amos.core.basic.utils.AmosUtils;
import org.amos.core.basic.utils.FileUtils;
import org.amos.core.generator.dto.*;
import org.amos.core.generator.entity.*;
import org.amos.core.generator.gen.CodeFile;
import org.amos.core.generator.gen.GenConfig;
import org.amos.core.generator.gen.GenInfo;
import org.amos.core.generator.gen.GenPreInfo;
import org.amos.core.generator.mapper.GenMapper;
import org.amos.core.generator.mapper.GenTableMapper;
import org.amos.core.generator.service.*;
import org.amos.core.generator.utils.GenUtil;
import org.amos.core.generator.utils.GenoldUtil;
import org.amos.core.generator.vo.GenColumnVo;
import org.amos.core.generator.vo.GenTableVo;
import org.amos.core.generator.vo.GenValidVo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenServiceImpl implements GenService {

    private final GenTableService tableInfoService;
    private final GenColumnInfoService columnInfoService;
    private final IGenDatasourceService genDatasourceService;
    private final GenTemplateService genTemplateService;
    private final GenUtil genUtil;
    private final GenTableMapper tableInfoMapper;
    private final GenValidInfoService genValidInfoService;
    private final GenMapper genMapper;

    @Override
    public IPage<AmosTable> getGenTableList(Page<AmosTable> page) {
        return genMapper.queryMySqlTablesPage(page);
    }

    @Override
    public IPage<AmosGenTableInfo> getGenTablePage(Page<AmosGenTableInfo> page, QueryWrapper queryWrapper) {
        return tableInfoService.page(page, queryWrapper);
    }

    @Override
    public List<GenColumnVo> getGenColDefaultList(String tableName) {
        return GenoldUtil.getFields(tableName);
    }

    @Override
    public List<GenColumnVo> getGenColumnList(String tableName) {
        QueryWrapper<AmosGenColumnInfo> qw = new QueryWrapper<>();
        qw.eq("table_name", tableName);
        List<AmosGenColumnInfo> columnInfos = columnInfoService.list(qw);
        return AmosUtils.listCopy(columnInfos, GenColumnVo.class);
    }

    @Override
    public List<GenValidVo> getGenCoValidDefaultlList(String tableName) {
        List<GenValidVo> vos = AmosUtils.listCopy(GenoldUtil.getFields(tableName), GenValidVo.class);
        vos.forEach(v -> {
            v.setIsNotNull(true);
            v.setComment(v.getComment());
        });
        return vos;
    }

    @Override
    public List<GenValidVo> getGenColumnValidList(String tableName) {
        QueryWrapper<AmosGenValidInfo> qw = new QueryWrapper<>();
        qw.eq("table_name", tableName);
        List<AmosGenValidInfo> validInfos = genValidInfoService.list(qw);
        return AmosUtils.listCopy(validInfos, GenValidVo.class);
    }

    @Override
    public GenTableVo getGenTableInfo(String tableName) {
        QueryWrapper<AmosGenTableInfo> qw = new QueryWrapper<>();
        qw.eq("table_name", tableName);
        AmosGenTableInfo info = tableInfoService.getOne(qw);
        return ObjectUtil.isEmpty(info) ? null : AmosUtils.copy(info, GenTableVo.class);
    }

    @Override
    public GenTableVo getGenTableDefaultInfo(String tableName) {
        GenTableVo vo = new GenTableVo();
        vo.setTableName(tableName);
        vo.setAuthor("刘伯韬");
        vo.setModuleName("AmosGen");
        vo.setPackageName("org.amos.gen.test");
        return vo;
    }

    @Override
    public Boolean saveGenTableInfo(GenTableDTO dto) {
        return tableInfoService.save(AmosUtils.copy(dto, AmosGenTableInfo.class));
    }

    @Override
    public Boolean updateGenTableInfo(GenTableDTO dto) {
        UpdateWrapper<AmosGenTableInfo> uw = new UpdateWrapper<>(AmosUtils.copy(dto, AmosGenTableInfo.class));
        return tableInfoService.update(uw);
    }

    @Override
    public Boolean saveGenColumnInfo(GenColDTO dto) {
        return columnInfoService.save(AmosUtils.copy(dto, AmosGenColumnInfo.class));
    }

    @Override
    public Boolean updateGenColumnInfo(GenColDTO dto) {
        UpdateWrapper<AmosGenColumnInfo> uw = new UpdateWrapper<>(AmosUtils.copy(dto, AmosGenColumnInfo.class));
        return columnInfoService.update(uw);
    }

    @Override
    public Boolean genCode(Long id) {
        //生成Java代码
        GenPreInfo preInfo = this.genCommon(id);
        genUtil.genCodeDefault(preInfo.getGenInfo(), preInfo.getGenConfig());
        //生成HTML代码
        return Boolean.TRUE;
    }

    @Override
    public void downloadCode(Long id, HttpServletResponse response) {
        //生成Java代码
        GenPreInfo preInfo = this.genCommon(id);
        String filePath = genUtil.genCodeDownload(preInfo.getGenInfo(), preInfo.getGenConfig());
        //生成HTML代码

        File file = new File(filePath);
        String zipPath = file.getPath() + ".zip";
        ZipUtil.zip(file.getPath(), zipPath);
        FileUtils.downloadFile(response, new File(zipPath), Boolean.TRUE);
    }

    @Override
    public Object previewCode(Long id) {
        //代码预览
        GenPreInfo preInfo = this.genCommon(id);
        List<CodeFile> codeFiles = genUtil.genCodePreview(preInfo.getGenInfo(), preInfo.getGenConfig());
        return codeFiles;
    }

    @Override
    public GenPreInfo genCommon(Long id) {
        AmosGenTableInfo info = tableInfoService.getById(id);
        List<String> tableNames = new ArrayList<>();
        List<Long> templates = new ArrayList<>();

        Long datasourceId = info.getDataSourceId();
        Long groupId = info.getGroupId();
        GenInfo genInfo = AmosUtils.copy(info, GenInfo.class);

        QueryWrapper<GenTemplate> qw = new QueryWrapper<>();
        qw.eq("group_id", groupId);
        List<GenTemplate> genTemplateList = genTemplateService.list(qw);
        genTemplateList.forEach(t -> {
            templates.add(t.getId());
        });
        tableNames.add(info.getTableName());

        genInfo.setTableNames(tableNames);
        genInfo.setTemplateIds(templates);
        genInfo.setDatasourceId(datasourceId);
        GenDatasource datasourceConfig = genDatasourceService.getById(datasourceId);
        GenConfig genConfig = GenConfig.build(datasourceConfig);

        GenPreInfo preInfo = new GenPreInfo();
        preInfo.setGenInfo(genInfo);
        preInfo.setGenConfig(genConfig);

        return preInfo;
    }

    @Override
    public Boolean saveGenSetting(GenSettingDTO dto) {
        List<GenColumnInfoDTO> columns = dto.getColumns();
        List<GenValidDTO> valids = dto.getValids();
        GenTableDTO table = dto.getTable();

        tableInfoService.saveOrUpdate(AmosUtils.copy(table, AmosGenTableInfo.class));
        columnInfoService.saveOrUpdateBatch(AmosUtils.listCopy(columns, AmosGenColumnInfo.class));
        genValidInfoService.saveOrUpdateBatch(AmosUtils.listCopy(valids, AmosGenValidInfo.class));

        return Boolean.TRUE;
    }

}
