package org.amos.core.generator.gen.postgresql;

import org.amos.core.generator.gen.ColumnDefinition;
import org.amos.core.generator.gen.ColumnSelector;
import org.amos.core.generator.gen.GenConfig;
import org.amos.core.generator.utils.FieldUtil;

import java.util.Map;
import java.util.Set;

/**
 * @author tanghc
 */
public class PostgreSqlColumnSelector extends ColumnSelector {

    private static final PostgreSqlTypeFormatter SQL_TYPE_FORMATTER = new PostgreSqlTypeFormatter();

    public PostgreSqlColumnSelector(GenConfig dataBaseConfig) {
        super(dataBaseConfig);
    }

    private static final String SHOW_COLUMN_SQL = "SELECT  " +
            " pg_attribute.attname AS colname,  " +
            " atttypid::regtype AS type,  " +
            " col_description ( pg_attribute.attrelid, pg_attribute.attnum ) AS cmt,  " +
            " pg_attribute.attnum = pg_constraint.conkey [ 1 ] AS is_pk,  " +
            "CASE WHEN POSITION ( 'nextval' IN column_default ) > 0 THEN 1 ELSE 0 END AS is_identity  " +
            "FROM  " +
            " pg_constraint  " +
            " INNER JOIN pg_class ON pg_constraint.conrelid = pg_class.oid  " +
            " INNER JOIN pg_attribute ON pg_attribute.attrelid = pg_class.oid  " +
            " INNER JOIN pg_type ON pg_type.oid = pg_attribute.atttypid  " +
            " INNER JOIN information_schema.COLUMNS C ON C.TABLE_NAME = pg_class.relname   " +
            " AND C.COLUMN_NAME = pg_attribute.attname   " +
            "WHERE  " +
            " pg_class.relname = '%s'   " +
            " AND pg_attribute.attnum > 0";

    @Override
    protected String getColumnInfoSQL(String tableName) {
        return String.format(SHOW_COLUMN_SQL, tableName);
    }

    /*
    "colname"	    "type"	                        "cmt"	    "is_pk"	"is_identity"
    "id"	        "integer"	                    "自增主键"	   "t"	"1"
    "user_id"	    "integer"	                    "用户id"	    "f"	"0"
    "city"	        "character varying"	            "城市"	        "f"	"0"
    "address"	    "character varying"	            "街道"	        "f"	"0"
    "status"	    "character varying"	            "类型"	        "f"	"0"
    "create_time"	"timestamp without time zone"	"添加时间"	    "f"	"0"
    "update_time"	"timestamp without time zone"	"修改时间"	    "f"	"0"

     */
    @Override
    protected ColumnDefinition buildColumnDefinition(Map<String, Object> rowMap) {
        Set<String> columnSet = rowMap.keySet();
        for (String columnInfo : columnSet) {
            rowMap.put(columnInfo.toUpperCase(), rowMap.get(columnInfo));
        }

        ColumnDefinition columnDefinition = new ColumnDefinition();

        columnDefinition.setColumnName(FieldUtil.convertString(rowMap.get("COLNAME")));

        boolean isIdentity = "1".equals(FieldUtil.convertString(rowMap.get("IS_IDENTITY")));
        columnDefinition.setIsIdentity(isIdentity);

        boolean isPk = (Boolean) rowMap.get("IS_PK");
        columnDefinition.setIsPk(isPk);

        String type = FieldUtil.convertString(rowMap.get("TYPE"));
        columnDefinition.setType(SQL_TYPE_FORMATTER.format(type));

        columnDefinition.setComment(FieldUtil.convertString(rowMap.get("CMT")));

        return columnDefinition;
    }

}
