package codigoalvo.filtros;

import java.io.IOException;
import java.io.Serializable;
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
import codigoalvo.controle.ControleLogin;
import codigoalvo.modelo.entidades.TipoUsuario;

@WebFilter(urlPatterns={"/privado/*", "/admin/*"})
public class FiltroSeguranca implements Serializable, Filter {

    private static final long serialVersionUID = -3140995657268553469L;
    private static final String FACES = ".jsf";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
	HttpServletRequest httpRequest = (HttpServletRequest)request;
	HttpServletResponse httpResponse = (HttpServletResponse)response;
	HttpSession sessao = httpRequest.getSession();
	String contextPath = httpRequest.getContextPath();
	ControleLogin controleLogin = (ControleLogin)sessao.getAttribute("controleLogin");
	if (controleLogin == null || controleLogin.getUsuarioLogado() == null) {
	    httpResponse.sendRedirect(contextPath+"/login"+FACES);
	} else {
	    String pagina = httpRequest.getRequestURL().toString();
	    if(pagina.contains("/admin/")) {
		if (!controleLogin.getUsuarioLogado().getTipo().equals(TipoUsuario.ADMIN)) {
		    httpResponse.sendRedirect(contextPath+"/naoAutorizado"+FACES);
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
