package org.amos.core.generator.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class GenColDTO {
    @Valid
    @NotEmpty(message = "列配置信息不能为空")
    private List<GenColumnInfoDTO> columnsInfo;
}
