package org.amos.core.basic.utils;

import cn.hutool.core.io.FileUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
/**
 * @desc: 文件工具类 封装
 * @author: liubt
 * @date: 2020-12-31 13:21
 **/
public class FileUtils extends FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    public static void downloadFile(HttpServletResponse response, File file, boolean deleteOnExit) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            response.reset();
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            IOUtils.copy(fis, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                    if (deleteOnExit) {
                        file.deleteOnExit();
                    }
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }
}
