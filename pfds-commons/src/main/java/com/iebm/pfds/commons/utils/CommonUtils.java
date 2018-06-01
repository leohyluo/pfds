package com.iebm.pfds.commons.utils;

import com.iebm.pfds.commons.exception.FwSpsoftExceptionCode;
import com.iebm.pfds.commons.exception.SpsoftException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用工具类
 * 
 * @author lining
 */
public class CommonUtils {

	private static final String UNKNOWN = "unknown";

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);
	/**
	 * IP地址正则表达式
	 */
	private static final String REG = "^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$";

	private CommonUtils() {
	}

	/**
	 * 新增cookie 默认根路径且当浏览器关闭时删除cookie
	 * 
	 * @param response
	 * @param key
	 *            cookie名称
	 * @param value
	 *            cookie值
	 * @param domain
	 *            cookie作用域
	 * @param secure
	 *            cookie安全性设置 true表示只支持https和ssl协议发送
	 */
	public static void addCookie(HttpServletResponse response, String key, String value, String domain, String secure) {
		setCookie(response, key, value, domain, secure, -1, null);
	}

	/**
	 * 新增cookie 当浏览器关闭时删除cookie
	 * 
	 * @param response
	 * @param key
	 *            cookie名称
	 * @param value
	 *            cookie值
	 * @param domain
	 *            cookie作用域
	 * @param secure
	 *            cookie安全性设置 true表示只支持https和ssl协议发送
	 */
	public static void addCookie(HttpServletResponse response, String key, String value, String domain, String secure,
			String path) {
		setCookie(response, key, value, domain, secure, -1, path);
	}

	/**
	 * 删除cookie
	 * 
	 * @param response
	 * @param key cookie名称
	 * @param domain cookie作用域
	 * @param secure 是否安全传递(仅https下传递)
	 */
	public static void removeCookie(HttpServletResponse response, String key, String domain, String secure) {
		setCookie(response, key, null, domain, secure, 0, null);
	}
	
	/**
	 * 删除cookie
	 * @param response
	 * @param key cookie名称
	 * @param domain cookie作用域
	 * @param secure 是否安全传递(仅https下传递)
	 * @param path cookie所在目录，默认为‘/’
	 */
	public static void removeCookie(HttpServletResponse response, String key, String domain, String secure,
			String path) {
		setCookie(response, key, null, domain, secure, 0, path);
	}

	/**
	 * 设置cookie
	 * 
	 * @param response
	 * @param key cookie名称
	 * @param value cookie值
	 * @param domain cookie作用域
	 * @param secure 是否安全传递(仅https下传递)
	 * @param maxAage -1表示关闭浏览器删除cookie， 0表示立即删除cookie
	 * @param path cookie所在目录，默认为‘/’
	 */
	private static void setCookie(HttpServletResponse response, String key, String value, String domain, String secure,
			int maxAage, String path) {
		Cookie cookie = new Cookie(key, value);
		cookie.setPath(StringUtils.isBlank(path) ? "/" : path);
		cookie.setMaxAge(maxAage);// 关闭浏览器删除cookie
		cookie.setSecure(Boolean.valueOf(secure));
		if (StringUtils.isNotBlank(domain)) {
			cookie.setDomain(domain);
		}
		response.addCookie(cookie);
	}

	/**
	 * 根据名称获取cookie值
	 * 
	 * @param request
	 * @param name cookie名
	 * @return cookie值
	 */
	public static String getCookieValByName(HttpServletRequest request, String name) {
		Cookie cookie = getCookie(request, name);
		return cookie == null ? null : cookie.getValue();
	}

	/**
	 * 根据cookie名称获取cookie
	 * 
	 * @param request
	 * @param name cookie名
	 * @return cookie对象
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = readCookieMap(request);
		return cookieMap.containsKey(name) ? cookieMap.get(name) : null;
	}

	/**
	 * 将cookie存入map中
	 * @param request
	 * @return
	 */
	private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<>();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}

		return cookieMap;
	}

	/**
	 * 获取 http(s)://host:port
	 * 
	 * @param request
	 * @return
	 */
	public static String getHost(HttpServletRequest request) {
		return getHttp(request) + request.getHeader("Host");
	}

	/**
	 * 网络请求协议头
	 * 
	 * @param request
	 * @return
	 */
	public static String getHttp(HttpServletRequest request) {
		return request.getScheme()+"://";
	}

	/**
	 * 判定是否是https协议请求 , 来设置cookie安全性
	 * 
	 * @param request
	 * @return true：表示是https
	 */
	public static String getCookieSecure(HttpServletRequest request) {
		return String.valueOf("https".equalsIgnoreCase(request.getScheme()));
	}

	/**
	 * 输出JSON
	 * 
	 * @param response
	 *            响应对象
	 * @param jsonObj
	 *            Json格式的字符串或则对象
	 * @throws IOException
	 */
	public static void responseJson(HttpServletResponse response, Object jsonObj) {
		response.setContentType("application/json;charset=utf-8");
		responseResult(response, jsonObj);
	}

	/**
	 * 直接输出问题描述
	 * 
	 * @param response
	 *            响应对象
	 * @param responseStr
	 *            输出字符串对象
	 */
	public static void responseText(HttpServletResponse response, Object responseStr) {
		response.setContentType("text/plain;charset=utf-8");
		responseResult(response, String.valueOf(responseStr));
	}

	/**
	 * 输出HTML到前台响应
	 * 
	 * @param response
	 * @param responseHtml
	 */
	public static void responseHtml(HttpServletResponse response, Object responseHtml) {
		response.setContentType("text/html;charset=utf-8");
		responseResult(response, String.valueOf(responseHtml));
	}

	/**
	 * 输出操作
	 * 
	 * @param response
	 * @param responseObj
	 */
	private static void responseResult(HttpServletResponse response, Object responseObj) {
		if (response == null || responseObj == null) {
			return;
		}

		try {
			PrintWriter out = response.getWriter();
			out.print(responseObj);
			out.flush();
			out.close();
		} catch (IOException e) {
			LOGGER.info(e.getMessage());
			throw new SpsoftException(FwSpsoftExceptionCode.IO_EXCEPTION.get());
		}
	}

	/**
	 * 输出图片到浏览器展示
	 * @param imgPath 本地图片路径
	 * @param response
	 */
	public static void responseImage(HttpServletResponse response, String imgPath) {
		if (response == null || imgPath == null) {
			return;
		}

		File outImgFile = new File(imgPath);
		if (outImgFile.exists() && outImgFile.isFile()) {
			response.setContentType("image/jpeg;image/png;image/gif; charset=GBK");
			try (ServletOutputStream os = response.getOutputStream();
					FileInputStream is = new FileInputStream(new File(imgPath))) {
				byte[] buffer = new byte[1024];
				int i;
				while ((i = is.read(buffer)) != -1) {
					os.write(buffer, 0, i);
				}
				os.flush();
				os.close();
				is.close();
			} catch (IOException e) {
				LOGGER.info(e.getMessage());
				throw new SpsoftException(FwSpsoftExceptionCode.IO_EXCEPTION.get());
			}
		} else {
			responseText(response, "电子图片不存在");
		}

	}

	/**
	 * 获取客户端请求的实际IP地址
	 * 
	 * @param request
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getIPAddr(HttpServletRequest request) {

		if (request == null) {
			return StringUtils.EMPTY;
		}

		String ipAddr = request.getHeader("x-forwarded-for");

		if (StringUtils.isBlank(ipAddr) || UNKNOWN.equalsIgnoreCase(ipAddr)) {
			ipAddr = request.getHeader("Proxy-Client-IP");
		}

		if (StringUtils.isBlank(ipAddr) || UNKNOWN.equalsIgnoreCase(ipAddr)) {
			ipAddr = request.getHeader("WL-Proxy-Client-IP");
		}

		if (StringUtils.isBlank(ipAddr) || UNKNOWN.equalsIgnoreCase(ipAddr)) {
			ipAddr = request.getRemoteAddr();
			// 2130706433L为十进制的127.0.0.1
			if (ipAddr.matches(REG) && 2130706433L == ip2int(ipAddr)) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					LOGGER.info(e.getMessage());
					throw new SpsoftException(FwSpsoftExceptionCode.UNKNOW_EXCEPTION.get());
				}
				ipAddr = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP，多个IP按照逗号（,）分割
		if (StringUtils.length(ipAddr) > 15 && StringUtils.indexOf(ipAddr, ',') > 0) {
			ipAddr = StringUtils.substring(ipAddr, 0, StringUtils.indexOf(ipAddr, ','));
		}

		return ipAddr;
	}

	public  static String getIPAddrByRequestContextHolder() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return getIPAddr(request);
	}


	/**
	 * ip转换为long<br>
	 * 10进制IP转换
	 * 
	 * @param ip
	 * @return
	 */
	public static long ip2int(String ip) {
		String[] items = ip.split("\\.");
		return Long.valueOf(items[0]) << 24 | Long.valueOf(items[1]) << 16 | Long.valueOf(items[2]) << 8
				| Long.valueOf(items[3]);
	}

	public static void clearCookie(HttpServletRequest request, HttpServletResponse response, String domain,
			String path) {
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			removeCookie(response, cookies[i].getName(), domain, "", path);
		}
	}
}
