package org.amos.core.generator.gen;

import lombok.Data;

import java.util.List;

@Data
public class GenInfo {

	private Long datasourceId;

	private List<String> tableNames;

	private List<Long> templateIds;

	private String author;

	private String packageName;

	private String tablePrefix;

	private String charset = "UTF-8";

}
