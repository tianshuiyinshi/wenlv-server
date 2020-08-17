package com.zte.common.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.zte.common.utils.AuthUtils;
import com.zte.common.utils.RedisKey;
import com.zte.common.utils.RedisOperUtils;
import com.zte.common.utils.SystemUtils;

/*
 * 重复请求过滤器
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "repeatReqFilter")
public class RepeatReqFilter implements Filter {
	private static Logger log = LoggerFactory.getLogger(RepeatReqFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse res = (HttpServletResponse) response;
		res.setHeader("Access-Control-Allow-Origin", "*");  
		res.setHeader("Access-Control-Allow-Headers", "*"); 
		res.setHeader("Access-Control-Max-Age", "3600");
		HttpServletRequest hrequest = (HttpServletRequest) request;
		String url = hrequest.getRequestURL().toString();
		if (url.contains("/add") || url.contains("/update")||url.contains("/del")||url.contains("/logoutOthers")) {
			if(!SystemUtils.checAdminToken(hrequest)) {
				HttpServletResponse hresponse = (HttpServletResponse) response;
				response.setContentType("application/json;charset=UTF-8");
				PrintWriter pw = hresponse.getWriter();
				JSONObject json = new JSONObject();
				json.put("code", "9999");
				json.put("innercode", "8888");
				json.put("message", "未经授权的请求");
				pw.write(json.toJSONString());
				pw.close();
				return;
			}
		}



	
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	private String getBodyString(BufferedReader br) {
		String inputLine;
		String str = "";
		try {
			while ((inputLine = br.readLine()) != null) {
				str += inputLine;
			}
			br.close();
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
		return str;
	}
}
