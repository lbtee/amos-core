package org.amos.starter.xss.core;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.amos.starter.xss.cleaner.XssCleaner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Request包装类: 用于 XSS 过滤
 *
 * @author Hccake
 * @version 1.0
 * @date 2019/10/16 10:29
 */
@Slf4j
public class XssRequestWrapper extends HttpServletRequestWrapper {

	private final XssCleaner xssCleaner;

	public XssRequestWrapper(HttpServletRequest request, XssCleaner xssCleaner) {
		super(request);
		this.xssCleaner = xssCleaner;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> map = new LinkedHashMap<>();
		Map<String, String[]> parameters = super.getParameterMap();
		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			String[] values = entry.getValue();
			for (int i = 0; i < values.length; i++) {
				values[i] = xssCleaner.clean(values[i]);
			}
			map.put(entry.getKey(), values);
		}
		return map;
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);
		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = xssCleaner.clean(values[i]);
		}
		return encodedValues;
	}

	@Override
	public Enumeration<String> getHeaders(String name) {
		List<String> headerList = new ArrayList<>();
		Enumeration<String> headers = super.getHeaders(name);
		while (headers.hasMoreElements()) {
			String nextElement = headers.nextElement();
			headerList.add(xssCleaner.clean(nextElement));
		}
		return Collections.enumeration(headerList);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException
	{
		// 非json类型，直接返回
		if (!isJsonRequest())
		{
			return super.getInputStream();
		}

		// 为空，直接返回
		String json = IoUtil.readUtf8(super.getInputStream());
		if (StrUtil.isEmpty(json))
		{
			return super.getInputStream();
		}

		// xss过滤
		json = xssCleaner.clean(json).trim();
		byte[] jsonBytes = json.getBytes("utf-8");
		final ByteArrayInputStream bis = new ByteArrayInputStream(jsonBytes);
		return new ServletInputStream()
		{
			@Override
			public boolean isFinished()
			{
				return true;
			}

			@Override
			public boolean isReady()
			{
				return true;
			}

			@Override
			public int available() throws IOException
			{
				return jsonBytes.length;
			}

			@Override
			public void setReadListener(ReadListener readListener)
			{
			}

			@Override
			public int read() throws IOException
			{
				return bis.read();
			}
		};
	}

	/**
	 * 是否是Json请求
	 *
	 */
	public boolean isJsonRequest()
	{
		String header = super.getHeader(HttpHeaders.CONTENT_TYPE);
		return StrUtil.startWithIgnoreCase(header, MediaType.APPLICATION_JSON_VALUE);
	}
}
