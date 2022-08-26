package org.amos.core.generator.utils;

import org.amos.core.generator.entity.GenTemplate;
import org.amos.core.generator.gen.SQLService;
import org.amos.core.generator.gen.SQLServiceFactory;
import org.amos.core.generator.gen.CodeFile;
import org.amos.core.generator.gen.GenConfig;
import org.amos.core.generator.gen.GenInfo;
import org.amos.core.generator.gen.SQLContext;
import org.amos.core.generator.gen.TableDefinition;
import org.amos.core.generator.gen.TableSelector;
import org.amos.core.generator.service.GenTemplateService;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 生成代码逻辑
 */
@Component
public class GenUtil {

    static ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Autowired
    private GenTemplateService genTemplateService;

    @Value("${gen.format-xml:false}")
    private String formatXml;

    /**
     * 基本路径
     */
    public static final String SRC_MAIN_JAVA = "src/main/java/";

    /**
     * 作者
     */
    public static final String AUTHOR = "LiuBoTao";

    /**
     * 生成代码内容,map的
     *
     * @param genInfo 生成参数
     * @param genConfig 数据源配置
     * @return 一张表对应多个模板
     */
    public List<CodeFile> genCode(GenInfo genInfo, GenConfig genConfig) {
        List<SQLContext> contextList = this.buildSQLContextList(genInfo, genConfig);
        List<CodeFile> codeFileList = new ArrayList<>();

        for (SQLContext sqlContext : contextList) {
            setPackageName(sqlContext, genInfo.getPackageName());
            setDelPrefix(sqlContext, genInfo.getTablePrefix());
            for (Long tcId : genInfo.getTemplateIds()) {
                GenTemplate template = genTemplateService.getById(tcId);
                String folder = template.getName();
                String fileName = doGenerator(sqlContext, template.getFileName());
                String content = doGenerator(sqlContext, template.getContent());
                content = this.formatCode(fileName, content);
                CodeFile codeFile = new CodeFile();
                codeFile.setFolder(folder);
                codeFile.setFileName(fileName);
                codeFile.setContent(content);
                codeFileList.add(codeFile);
            }
        }

        return codeFileList;
    }

    /**
     * 默认代码生成到工程
     * @param genInfo
     * @param genConfig
     * @return
     */
    public Boolean genCodeDefault(GenInfo genInfo, GenConfig genConfig){
        String projectPath = System.getProperty("user.dir");
        String modelPath = "/amos-modules/";
        String basePath = projectPath + modelPath + SRC_MAIN_JAVA + genInfo.getPackageName().replace(".", "/");
        genCode(genInfo, genConfig).forEach(codeFile -> {
            String path = basePath + File.separator + codeFile.getFolder() + File.separator + codeFile.getFileName();
            File file = new File(path);
            try {
                VelocityUtil.genFile(file, codeFile.getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        return Boolean.TRUE;
    }

    /**
     * 代码生成预览
     * @param genInfo
     * @param genConfig
     * @return
     */
    public List<CodeFile> genCodePreview(GenInfo genInfo, GenConfig genConfig){
        return genCode(genInfo, genConfig);
    }

    /**
     * 下载生成代码
     * @param genInfo
     * @param genConfig
     * @return
     */
    public String genCodeDownload(GenInfo genInfo, GenConfig genConfig){
        String systemPath = System.getProperty("java.io.tmpdir");
        String filePath = systemPath + File.separator + "amos-gen-code" + File.separator + String.join("-", genInfo.getTableNames());
        String modelPath = "/amos-modules/";
        String basePath = filePath + modelPath + SRC_MAIN_JAVA + genInfo.getPackageName().replace(".", "/");
        genCode(genInfo, genConfig).forEach(codeFile -> {
            String path = basePath + File.separator + codeFile.getFolder() + File.separator + codeFile.getFileName();
            File file = new File(path);
            try {
                VelocityUtil.genFile(file, codeFile.getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        return filePath;
    }

    // 格式化代码
    private String formatCode(String fileName, String content) {
        if (Objects.equals("true", formatXml) && fileName.endsWith(".xml")) {
            return FormatUtil.formatXml(content);
        }
        return content;
    }


    /**
     * 返回SQL上下文列表
     *
     * @param genInfo 参数
     * @param genConfig 配置
     * @return 返回SQL上下文
     */
    private List<SQLContext> buildSQLContextList(GenInfo genInfo, GenConfig genConfig) {

        List<String> tableNames = genInfo.getTableNames();
        List<SQLContext> contextList = new ArrayList<>();
        SQLService service = SQLServiceFactory.build(genConfig);

        TableSelector tableSelector = service.getTableSelector(genConfig);
        tableSelector.setSchTableNames(tableNames);

        List<TableDefinition> tableDefinitions = tableSelector.getTableDefinitions();

        for (TableDefinition tableDefinition : tableDefinitions) {
            SQLContext sqlContext = new SQLContext(tableDefinition);
            sqlContext.setDbName(genConfig.getDbName());
            contextList.add(sqlContext);
        }

        return contextList;
    }

    private void setPackageName(SQLContext sqlContext, String packageName) {
        if (StringUtils.hasText(packageName)) {
            sqlContext.setPackageName(packageName);
        }
    }

    private void setDelPrefix(SQLContext sqlContext, String delPrefix) {
        if (StringUtils.hasText(delPrefix)) {
            sqlContext.setDelPrefix(delPrefix);
        }
    }

    private String doGenerator(SQLContext sqlContext, String template) {
        if (template == null) {
            return "";
        }
        VelocityContext context = new VelocityContext();

        context.put("context", sqlContext);
        context.put("table", sqlContext.getTableDefinition());
        context.put("pk", sqlContext.getTableDefinition().getPkColumn());
        context.put("columns", sqlContext.getTableDefinition().getColumnDefinitions());

        return VelocityUtil.genCode(context, template);
    }

}
