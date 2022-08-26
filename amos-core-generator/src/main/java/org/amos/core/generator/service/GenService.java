package org.amos.core.generator.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.amos.core.generator.entity.AmosGenTableInfo;
import org.amos.core.generator.entity.AmosTable;
import org.amos.core.generator.dto.GenColDTO;
import org.amos.core.generator.dto.GenSettingDTO;
import org.amos.core.generator.dto.GenTableDTO;
import org.amos.core.generator.gen.GenPreInfo;
import org.amos.core.generator.vo.GenColumnVo;
import org.amos.core.generator.vo.GenTableVo;
import org.amos.core.generator.vo.GenValidVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface GenService {
    /**
     * 数据表信息列表
     * @return
     */
    IPage<AmosTable> getGenTableList(Page<AmosTable> page);

    /**
     * 自定义数据表信息列表
     * @return
     */
    IPage<AmosGenTableInfo> getGenTablePage(Page<AmosGenTableInfo> page, QueryWrapper queryWrapper);

    /**
     * 根据表名获取列配置信息
     * @param tableName
     * @return
     */
    List<GenColumnVo> getGenColDefaultList(String tableName);

    /**
     * 根据表名获取列配置信息
     * @param tableName
     * @return
     */
    List<GenColumnVo> getGenColumnList(String tableName);

    /**
     * 根据表名获取字段校验默认配置信息
     * @param tableName
     * @return
     */
    List<GenValidVo> getGenCoValidDefaultlList(String tableName);

    /**
     * 根据表名获取字段校验配置信息
     * @param tableName
     * @return
     */
    List<GenValidVo> getGenColumnValidList(String tableName);

    /**
     * 根据表名获取表配置信息
     * @param tableName
     * @return
     */
    GenTableVo getGenTableInfo(String tableName);

    /**
     * 根据表名获取表配置信息
     * @param tableName
     * @return
     */
    GenTableVo getGenTableDefaultInfo(String tableName);
    /**
     * 保存表配置信息
     * @param dto
     * @return
     */
    Boolean saveGenTableInfo(GenTableDTO dto);
    /**
     * 更新表配置信息
     * @param dto
     * @return
     */
    Boolean updateGenTableInfo(GenTableDTO dto);
    /**
     * 保存列配置信息
     * @param dto
     * @return
     */
    Boolean saveGenColumnInfo(GenColDTO dto);
    /**
     * 更新列配置信息
     * @param dto
     * @return
     */
    Boolean updateGenColumnInfo(GenColDTO dto);

    /**
     * 根据表配置ID生成代码
     * @param id
     * @return
     */
    Boolean genCode(Long id);

    /**
     * 下载生成代码
     * @param id
     * @return
     */
    void downloadCode(Long id, HttpServletResponse response);

    /**
     * 根据表名生成预览代码
     * @param id
     * @return
     */
    Object previewCode(Long id);

    /**
     * 代码生成通用部分
     * @param id
     * @return
     */
    GenPreInfo genCommon(Long id);

    /**
     * 保存代码生成配置
     * @param dto
     * @return
     */
    Boolean saveGenSetting(GenSettingDTO dto);

}
