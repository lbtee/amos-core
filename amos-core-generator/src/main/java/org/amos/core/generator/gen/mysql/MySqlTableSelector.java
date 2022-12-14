package org.amos.core.generator.gen.mysql;

import org.amos.core.generator.gen.GenConfig;
import org.amos.core.generator.gen.TableSelector;
import org.amos.core.generator.gen.TableDefinition;
import org.amos.core.generator.gen.ColumnSelector;

import java.util.Map;

/**
 * 查询mysql数据库表
 */
public class MySqlTableSelector extends TableSelector {

	public MySqlTableSelector(ColumnSelector columnSelector,
                              GenConfig dataBaseConfig) {
		super(columnSelector, dataBaseConfig);
	}

	@Override
	protected String getShowTablesSQL(String dbName) {
		// 兼容dbName包含'-'字段会报错的情况
		dbName = dbName.contains("-") ? String.format("`%s`",dbName): dbName;
		String sql = "SHOW TABLE STATUS FROM " + dbName;
		if(this.getSchTableNames() != null && this.getSchTableNames().size() > 0) {
			StringBuilder tables = new StringBuilder();
			for (String table : this.getSchTableNames()) {
				tables.append(",'").append(table).append("'");
			}
			sql += " WHERE NAME IN (" + tables.substring(1) + ")";
		}
		return sql;
	}

	@Override
	protected TableDefinition buildTableDefinition(Map<String, Object> tableMap) {
		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition.setTableName((String)tableMap.get("NAME"));
		tableDefinition.setComment((String)tableMap.get("COMMENT"));
		return tableDefinition;
	}

}
