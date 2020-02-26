package com.ipartek.formacion.controller.filter;

import java.io.IOException;
import java.net.HttpRetryException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ipartek.formacion.controller.controller.LoginController;

/**
 * Servlet Filter implementation class AppFilter
 */
@WebFilter(dispatcherTypes = {
				DispatcherType.REQUEST,
				DispatcherType.FORWARD,
				DispatcherType.INCLUDE,
				DispatcherType.ERROR
		}
					, urlPatterns = { "/*" })
public class AppFilter implements Filter {

	private final static Logger LOG = Logger.getLogger(AppFilter.class);


    /**
     * Default constructor.
     */
    public AppFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		HttpServletResponse res = (HttpServletResponse) response;

		res.addHeader("Access-Control-Allow-Origin", "*");
		res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
		res.addHeader("Access-Control-Allow-Headers", "Content-Type");


		HttpServletRequest req = (HttpServletRequest) request;
		if(req.getCookies() != null) {

			for(Cookie cookie : req.getCookies()) {
				LOG.trace(cookie.getName() + "::" + cookie.getValue());
			}
		} else {
			LOG.trace("No tiene cookies");
		}


		// pass the request along the filter chain
		chain.doFilter(request, res);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
