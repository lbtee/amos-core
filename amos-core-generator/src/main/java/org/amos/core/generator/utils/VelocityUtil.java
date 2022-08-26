package org.amos.core.generator.utils;

import cn.hutool.core.io.FileUtil;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.log.NullLogChute;

import java.io.*;

/**
 * Velocity工具类,根据模板内容生成文件
 */
public class VelocityUtil {
	static {
		// 禁止输出日志
		Velocity.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM, new NullLogChute());
		Velocity.init();
	}

	public static String genCode(VelocityContext context, String template) {
		StringReader reader = new StringReader(template);
		StringWriter writer = new StringWriter();
		// 不用vm文件
		Velocity.evaluate(context, writer, "mystring", reader);

		try {
			writer.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return writer.toString();
	}

	public static void genFile(File file, String code) throws IOException {
		Writer writer = null;
		BufferedWriter bw = null;
		try {

			FileUtil.touch(file);
			writer = new FileWriter(file);
			bw = new BufferedWriter(writer);
			//  将读入的字符串写入到文件中
			bw.write(code,  0 , code.length());
			bw.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			assert writer != null;
			writer.close();
			bw.close();
		}
	}
}
