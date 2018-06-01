/*
package com.iebm.pfds.commons.security;

import org.springframework.http.*;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

*/
/**
 * Oauth2资源 错误处理类
 * @author LiNing
 *
 *//*

public class HeaderOnlyOAuth2ExceptionRenderer implements
		OAuth2ExceptionRenderer {

	*/
/**
	 * oauth2.0 访问受限后，跳转到输出错误信息的方法
	 *//*

	@Override
	public void handleHttpEntityResponse(HttpEntity<?> responseEntity,
			ServletWebRequest webRequest) throws Exception {
		if (responseEntity == null) {
			return;
		}
		@SuppressWarnings("unused")
        HttpInputMessage inputMessage = createHttpInputMessage(webRequest);
		HttpOutputMessage outputMessage = createHttpOutputMessage(webRequest);
		if (responseEntity instanceof ResponseEntity
				&& outputMessage instanceof ServerHttpResponse) {
			((ServerHttpResponse) outputMessage)
					.setStatusCode(((ResponseEntity<?>) responseEntity)
							.getStatusCode());
		}
		
		HttpHeaders entityHeaders = responseEntity.getHeaders();
		if (!entityHeaders.isEmpty()) {
			outputMessage.getHeaders().putAll(entityHeaders);
		}	
		//((ResponseEntity<?>) responseEntity).getStatusCode() 状态code
		// 输出错误信息到页面
		CommonUtils.responseJson(webRequest.getResponse(), responseEntity.getBody());
	}
	
	private HttpInputMessage createHttpInputMessage(NativeWebRequest webRequest){
		HttpServletRequest servletRequest = webRequest
				.getNativeRequest(HttpServletRequest.class);
		return new ServletServerHttpRequest(servletRequest);
	}

	private HttpOutputMessage createHttpOutputMessage(
			NativeWebRequest webRequest){
		HttpServletResponse servletResponse = (HttpServletResponse) webRequest
				.getNativeResponse();
		return new ServletServerHttpResponse(servletResponse);
	}

}
*/
