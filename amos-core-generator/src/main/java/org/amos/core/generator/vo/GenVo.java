package org.amos.core.generator.vo;

import lombok.Data;

import java.util.List;

@Data
public class GenVo {
    /**
     * 表信息
     */
    private GenTableVo tableInfo;
    /**
     * 列信息
     */
    private List<GenColumnInfoVo> columnInfos;
}
