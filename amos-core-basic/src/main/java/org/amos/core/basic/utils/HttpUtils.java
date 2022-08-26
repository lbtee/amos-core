package org.amos.core.basic.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.amos.core.basic.vo.R;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @desc: HTTP工具类 封装
 * @author: liubt
 * @date: 2020-12-31 13:21
 **/
@Slf4j
public class HttpUtils {
	/**
	 * 获取IP地址
	 * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
	 * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
	 */
	public static String getIp() {
		String UNKNOWN = "unknown";
		int MAX_LENGTH = 15;
		HttpServletRequest request = getRequest();
		String ip = null;
		try {
			ip = request.getHeader("x-forwarded-for");
			if (StrUtil.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (StrUtil.isEmpty(ip) || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (StrUtil.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (StrUtil.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (StrUtil.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} catch (Exception e) {
			log.error("getIp error ", e);
		}
		// 使用代理，则获取第一个IP地址
		if (!StrUtil.isEmpty(ip) && ip.length() > MAX_LENGTH) {
			if (ip.indexOf(StrUtil.COMMA) > 0) {
				ip = ip.substring(0, ip.indexOf(StrUtil.COMMA));
			}
		}
		return ip;
	}
	public static String getDeviceName(){
		String deviceName = "pc";
		String header = getRequest().getHeader(Header.USER_AGENT.getValue());
		boolean mobile = UserAgentUtil.parse(header).isMobile();
		if (mobile){
			deviceName = "mobile";
		}
		return deviceName;
	}

	public static String getURI() {
		return getRequest().getRequestURI();
	}

	/**
	 * 获取HttpServletRequest对象
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	/**
	 * 输出信息到浏览器
	 * @param response
	 * @throws IOException
	 */
	public static void write(HttpServletResponse response, R result) throws IOException {
		response.setContentType("application/json; charset=UTF-8");
        String json = JSONObject.toJSONString(result);
        response.getWriter().print(json);
        response.getWriter().flush();
        response.getWriter().close();
	}
	
}
