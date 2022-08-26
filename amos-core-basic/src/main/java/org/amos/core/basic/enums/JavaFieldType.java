package org.amos.core.basic.enums;

import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public enum JavaFieldType {
    INTEGER(0, DbColumnType.INTEGER, "Integer"),
    LONG(1, DbColumnType.LONG, "Long"),
    FLOAT(2, DbColumnType.FLOAT, "Float"),
    DOUBLE(3, DbColumnType.DOUBLE, "Double"),
    BIG_DECIMAL(4, DbColumnType.BIG_DECIMAL, "BigDecimal"),
    BOOLEAN(5, DbColumnType.BOOLEAN, "Boolean"),
    STRING(6, DbColumnType.STRING, "String"),
    DATE(7, DbColumnType.DATE, "Date");

    final int type;
    final DbColumnType typeEnum;
    final String name;

    public static IColumnType getName(Integer index) {
        for (JavaFieldType type : JavaFieldType.values()) {
            if (type.getType() == index) {
                return type.typeEnum;
            }
        }
        return DbColumnType.STRING;
    }

    public static String getName(IColumnType columnType) {
        for (JavaFieldType type : JavaFieldType.values()) {
            if (type.getTypeEnum().equals(columnType)) {
                return type.name;
            }
        }
        return DbColumnType.STRING.getType();
    }

    public static Integer getType(IColumnType columnType) {
        for (JavaFieldType type : JavaFieldType.values()) {
            if (type.getTypeEnum().equals(columnType)) {
                return type.type;
            }
        }
        return STRING.type;
    }

    public static List<JavaType> dictList(){
        List<JavaType> list = new ArrayList<>();
        for (JavaFieldType type : JavaFieldType.values()) {
            JavaType javaType = new JavaType();
            javaType.setTypeName(type.name);
            javaType.setTypeValue(type.type);
            list.add(javaType);
        }
        return list;
    }
}
