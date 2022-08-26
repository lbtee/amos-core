package org.amos.core.generator.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class DbTypeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String label;

    private Integer dbType;
}
