package com.ipartek.formacion.controller.filter;

import java.io.IOException;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Servlet Filter implementation class SeguridadFilter
 */
@WebFilter(dispatcherTypes = {
		DispatcherType.REQUEST,
		DispatcherType.FORWARD,
		DispatcherType.INCLUDE,
		DispatcherType.ERROR
}
			, urlPatterns = { "/api/*" })
public class SeguridadFilter implements Filter {

	private final static Logger LOG = Logger.getLogger(SeguridadFilter.class);


    /**
     * Default constructor.
     */
    public SeguridadFilter() {
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

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		HttpSession session = req.getSession();

		if("GET".equals(req.getMethod())) {
			// si solo quiere hacer get le dejamos pasar
			LOG.trace("Como solo quiere hacer GET le dejamos pasar");
			chain.doFilter(request, response);
		} else {
			// Si quiere hacer algo que no sea GET miramos si esta logeado
			if (session.getAttribute("usuario") == null) {
				// Si no esta logeado no le dejamos y le mandamos 403
				LOG.warn("Intentan entrar sin logearse");
				res.setStatus(HttpServletResponse.SC_FORBIDDEN);

			} else {
				// Si esta logeado le dejamos pasar
				LOG.info("Usuario logeaod correctamente");
				chain.doFilter(request, response);
			}
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
