package org.amos.core.generator.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.amos.core.basic.enums.JavaFieldType;
import org.amos.core.basic.utils.AmosUtils;
import org.amos.core.generator.entity.AmosGenTableInfo;
import org.amos.core.generator.gen.AmosGenInfo;
import org.amos.core.generator.gen.AmosGenerator;
import org.amos.core.generator.vo.GenColumnVo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GenoldUtil {
    /**
     * 代码生成位置
     */
    public static final String PARENT_NAME = "org.springblade.circle.admin";

    /**
     * modular 名字
     */
    public static final String MODULAR_NAME = "";

    /**
     * 基本路径
     */
    public static final String SRC_MAIN_JAVA = "src/main/java/";

    /**
     * 作者
     */
    public static final String AUTHOR = "CodeGenerator";

    /**
     * 是否是 rest 接口
     */
    private static final boolean REST_CONTROLLER_STYLE = true;

    public static final String JDBC_MYSQL_URL = "jdbc:mysql://rm-bp10yl7nqse9z6n47to.mysql.rds.aliyuncs.com:3306/app?" +
            "serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf8";

    public static final String JDBC_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    public static final String JDBC_USERNAME = "root";

    public static final String JDBC_PASSWORD = "1qaz!QAZ";

    public static void main(String[] args) {
        String moduleName = "user";
        String tableName = "views_gen_column_info";
        String tablePrefix = "views_";
        List<AmosGenTableInfo> list = new LinkedList<>();
        AmosGenTableInfo t1 = new AmosGenTableInfo();
        AmosGenTableInfo t2 = new AmosGenTableInfo();
//        AmosGenTableInfo t3 = new AmosGenTableInfo();
//        ViewsGenTableInfo t4 = new ViewsGenTableInfo();
//        ViewsGenTableInfo t5 = new ViewsGenTableInfo();
//        ViewsGenTableInfo t6 = new ViewsGenTableInfo();
//        ViewsGenTableInfo t7 = new ViewsGenTableInfo();
//        ViewsGenTableInfo t8 = new ViewsGenTableInfo();
//        ViewsGenTableInfo t9 = new ViewsGenTableInfo();
        t1.setModuleName("aiche");
        t1.setTablePrefix("app_");
//        t1.setClassComment("代码生成字段校验");
        t1.setTableName("app_identity_aiche_menu");

//        t2.setModuleName("system");
//        t2.setTablePrefix("amos_");
//        t2.setClassComment("字典简介");
//        t2.setTableName("amos_gen_template_group");

//        t3.setModuleName("system");
//        t3.setTablePrefix("views_");
//        t3.setClassComment("字典详情");
//        t3.setTableName("views_dict_detail");
//
//
//        t4.setModuleName("system");
//        t4.setTablePrefix("views_");
//        t4.setClassComment("菜单信息");
//        t4.setTableName("views_menu");
//
//        t5.setModuleName("system");
//        t5.setTablePrefix("views_");
//        t5.setClassComment("角色信息");
//        t5.setTableName("views_role");
//
//        t6.setModuleName("system");
//        t6.setTablePrefix("views_");
//        t6.setClassComment("角色菜单关联");
//        t6.setTableName("views_role_menu");
//
//        t7.setModuleName("system");
//        t7.setTablePrefix("views_");
//        t7.setClassComment("用户信息");
//        t7.setTableName("views_user");
//
//        t8.setModuleName("system");
//        t8.setTablePrefix("views_");
//        t8.setClassComment("用户角色关联");
//        t8.setTableName("views_user_role");

        list.add(t1);
//        list.add(t2);
//        list.add(t3);
//        list.add(t4);
//        list.add(t5);
//        list.add(t6);
//        list.add(t7);
//        list.add(t8);

//        List<GenColumnInfo> list = new ArrayList<>();
//        GenColumnInfo columnInfo = new GenColumnInfo();
//        columnInfo.setColumnName("user_name");
//        columnInfo.setColumnComment("这尼玛");
//        columnInfo.setFieldName("LBT");
//        columnInfo.setFieldType(6);
//        columnInfo.setFieldComment("这尼玛 成了");
//        list.add(columnInfo);
//        tableInfo.setColumns(list);
        //模块名、表名、表前缀
        for (AmosGenTableInfo t : list){
            genJavaCode(AmosUtils.copy(t, AmosGenInfo.class));
        }

//        List<AmosColumn> tableFields = getFields("amos_user");

//        System.out.println(tableFields);

    }

    public static <T> List<GenColumnVo> getFields(String tableName){
        ConfigBuilder configBuilder = new ConfigBuilder(null, getDataSourceConfig(), getStrategyConfig(tableName,""), null, null);
        List<TableInfo> tableInfoList = configBuilder.getTableInfoList();
        if (tableInfoList.size() > 0){
            List<GenColumnVo> columns = new ArrayList<>();
            tableInfoList.get(0).getFields().forEach(t -> {
                GenColumnVo c = new GenColumnVo();
                c.setTableName(tableName);
                c.setColumnName(t.getColumnName());
                c.setColumnType(t.getType());
                c.setFieldName(t.getPropertyName());
                c.setFieldType(JavaFieldType.getType(t.getColumnType()));
                c.setComment(t.getComment());
                c.setIsRequired(Boolean.TRUE);
                c.setIsInsert(Boolean.FALSE);
                c.setIsEdit(Boolean.FALSE);
                c.setIsList(Boolean.FALSE);
                c.setIsQuery(Boolean.FALSE);
                columns.add(c);
            });
            return columns;
        }
        return new ArrayList<>();
    }

    public static void genJavaCode(AmosGenInfo genInfo) {
        AmosGenerator generator = new AmosGenerator();
        generator.setGlobalConfig(getGlobalConfig());
        generator.setDataSource(getDataSourceConfig());
        generator.setPackageInfo(getPackageConfig(genInfo.getModuleName()));
        generator.setStrategy(getStrategyConfig(genInfo.getTableName(), genInfo.getTablePrefix()));
        generator.setCfg(getInjectionConfig(genInfo.getModuleName()));
        generator.setTemplateEngine(new FreemarkerTemplateEngine());
        generator.setAmosGenInfo(genInfo);
        generator.setTemplate(getTemplateConfig());
        generator.execute();
    }

    public static void genHTMLCode() {}

    public static void preview(){}

    private static String getDateTime() {
        LocalDateTime localDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDate.format(formatter);
    }

    private static InjectionConfig getInjectionConfig(final String moduleName) {
        return new InjectionConfig() {
            @Override
            public void initMap() {
                Map map = new HashMap();
                map.put("dateTime", getDateTime());
                setMap(map);
                final String projectPath = System.getProperty("user.dir");
                List<FileOutConfig> focs = new ArrayList<FileOutConfig>();
                // 自定义配置会被优先输出
                focs.add(new FileOutConfig("/templates/mapper.xml.ftl") {
                    @Override
                    public String outputFile(TableInfo tableInfo) {
                        // 自定义输出文件名，如果entity设置了前后缀，此次注意xml的名称也会跟着发生变化
                        return projectPath + "/src/main/resources/mapper/" +
                                moduleName + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                    }
                });
                focs.add(new FileOutConfig("/templates/entityDTO.java.ftl") {
                    @Override
                    public String outputFile(TableInfo tableInfo) {
                        // 自定义输出文件名，如果entity设置了前后缀，此次注意xml的名称也会跟着发生变化
                        return projectPath + "/" + SRC_MAIN_JAVA + PARENT_NAME.replace(".","/") + "/" + moduleName.toLowerCase() + "/dto/" + tableInfo.getEntityName() + "DTO" + StringPool.DOT_JAVA;
                    }
                });
                focs.add(new FileOutConfig("/templates/entityVO.java.ftl") {
                    @Override
                    public String outputFile(TableInfo tableInfo) {
                        // 自定义输出文件名，如果entity设置了前后缀，此次注意xml的名称也会跟着发生变化
                        return projectPath + "/" + SRC_MAIN_JAVA  + PARENT_NAME.replace(".","/") + "/" + moduleName.toLowerCase() + "/vo/" + tableInfo.getEntityName() + "VO" + StringPool.DOT_JAVA;
                    }
                });
                setFileOutConfigList(focs);
            }
        };
    }
    /**
     * 策略配置
     * @return
     */
    private static StrategyConfig getStrategyConfig(String tableName, String tablePrefix) {
        return new StrategyConfig()
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setInclude(tableName)
                .setRestControllerStyle(REST_CONTROLLER_STYLE)
                .setEntityLombokModel(Boolean.TRUE)// lombok 模型 @Accessors(chain = true)
                .setLogicDeleteFieldName("is_deleted")//逻辑删除字段名
                .setControllerMappingHyphenStyle(Boolean.TRUE)
                .setTablePrefix(tablePrefix)
                .setEntitySerialVersionUID(Boolean.FALSE)
                .setSuperEntityClass("org.amos.core.base.BaseEntity")
                .setSuperEntityColumns("id","create_by","create_time","update_by","update_time","is_deleted");
    }

    /**
     * 包名策略
     * @param moduleName
     * @return
     */
    private static PackageConfig getPackageConfig(String moduleName) {
        return new PackageConfig()
                .setModuleName(moduleName)
                .setParent(PARENT_NAME)
                .setController("controller")
                .setEntity("entity")
                .setMapper("mapper")
                .setService("service")
                .setServiceImpl("service.impl");
    }

    /**
     * 数据源配置
     * @return
     */
    private static DataSourceConfig getDataSourceConfig() {
//        DataSourceProperties dataSourceProperties = SpringContextHolder.getBean(DataSourceProperties.class);
        DbType dbType = DbType.MYSQL;
        ITypeConvert typeConvert = new MySqlTypeConvert();
        return new DataSourceConfig()
                .setDbType(dbType)
                .setTypeConvert(typeConvert)
                .setUrl(JDBC_MYSQL_URL)
                .setDriverName(JDBC_DRIVER_NAME)
                .setUsername(JDBC_USERNAME)
                .setPassword(JDBC_PASSWORD);
    }

    /**
     * 全局配置
     * @return
     */
    private static GlobalConfig getGlobalConfig() {
        String projectPath = System.getProperty("user.dir");
        String filePath = projectPath + "/" + MODULAR_NAME + SRC_MAIN_JAVA;

        if (false) {
//            if (SystemUtil.getOsInfo().isWindows()) {
            filePath = filePath.replaceAll("/+|\\\\+", "\\\\");
        } else {
            filePath = filePath.replaceAll("/+|\\\\+", "/");
        }
        return new GlobalConfig()
                .setOutputDir(filePath)
                .setDateType(DateType.ONLY_DATE)
                .setIdType(IdType.ASSIGN_ID)
                .setAuthor(AUTHOR)
                .setBaseColumnList(Boolean.TRUE)
                .setSwagger2(Boolean.TRUE)
                .setEnableCache(Boolean.FALSE)
                .setBaseResultMap(Boolean.TRUE)
                .setFileOverride(Boolean.TRUE)
                .setOpen(Boolean.FALSE)
                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                .setControllerName("%sController")
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setMapperName("%sMapper")
                .setXmlName("%sMapper")
                .setEntityName("%s");
    }

    /**
     * 模板配置
     * @return
     */
    private static TemplateConfig getTemplateConfig() {
        return new TemplateConfig()
                .setController("/templates/controller.java")
                .setService("/templates/service.java")
                .setServiceImpl("/templates/serviceImpl.java")
                .setEntity("/templates/entity.java")
                .setMapper("/templates/mapper.java")
                .setXml("/templates/mapper.xml");
    }
}
