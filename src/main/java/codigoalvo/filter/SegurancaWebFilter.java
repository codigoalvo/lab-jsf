package codigoalvo.filter;

import java.io.IOException;
import java.io.Serializable;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
// import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import codigoalvo.controller.LoginController;
import codigoalvo.entity.UsuarioTipo;

// @WebFilter(urlPatterns={"/privado/*", "/admin/*"}) //Colocado por enquanto no web.xml até tentar o spring-security
public class SegurancaWebFilter implements Serializable, Filter {

    private static final long serialVersionUID = -3140995657268553469L;
    private static final String FACES = ".jsf";

    private static final Logger LOGGER = Logger.getLogger(SegurancaWebFilter.class);

    @Inject
    LoginController loginController;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	throws IOException, ServletException {
	HttpServletRequest httpRequest = (HttpServletRequest)request;
	HttpServletResponse httpResponse = (HttpServletResponse)response;
	String contextPath = httpRequest.getContextPath();
	if (loginController == null) {
	    LOGGER.debug("injectedControleLogin: "+(loginController==null?"null":("usuario: "+loginController.getUsuarioLogado())));
	    loginController = (LoginController)httpRequest.getSession().getAttribute("loginController");
	}
	String pagina = httpRequest.getRequestURL().toString();
	if (loginController == null || loginController.getUsuarioLogado() == null) {
	    LOGGER.debug("controleLogin: "+(loginController==null?"null":("usuario: "+loginController.getUsuarioLogado())));
	    LOGGER.debug(httpRequest.getRemoteAddr() + " [!] " + pagina);
	    httpResponse.sendRedirect(contextPath + "/login" + FACES);
	} else {
	    if (pagina.contains("/admin/")) {
		if (!loginController.getUsuarioLogado().getTipo().equals(UsuarioTipo.ADMIN)) {
		    LOGGER.debug(httpRequest.getRemoteAddr() +"(" + loginController.getUsuarioLogado().getLogin() + ") [!] " + pagina);
		    httpResponse.sendRedirect(contextPath + "/denied" + FACES);
		}
	    }
	}
	chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig config)
	throws ServletException {}

    @Override
    public void destroy() {}

}
