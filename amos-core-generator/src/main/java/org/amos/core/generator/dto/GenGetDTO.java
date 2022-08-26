package org.amos.core.generator.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GenGetDTO {
    @NotBlank(message = "表名不能为空")
    private String tableName;
}
