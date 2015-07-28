package codigoalvo.controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import codigoalvo.entity.Usuario;
import codigoalvo.service.LoginService;
import codigoalvo.util.MsgUtil;

@Named
@Scope("session")
@ManagedBean(name = "controleLogin")
public class ControleLogin implements Serializable {

    private static final long serialVersionUID = 3670261714704666556L;

    public static final String PAG_LOGIN_URL = "login";
    public static final String PAG_HOME_URL = "home";

    @Inject
    private transient LoginService loginService;

    private Usuario usuarioLogado;
    private Integer tentativasInvalidas;

    private String login;
    private String senha;

    public ControleLogin() {
	Logger.getLogger(ControleLogin.class).debug("construct");
	tentativasInvalidas = 0;
    }

    public String paginaLogin() {
	return PAG_LOGIN_URL;
    }

    public String efetuarLogin() {
	FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":messages");
	try {
	    usuarioLogado = loginService.efetuarLogin(login, senha);
	    MsgUtil.enviarMsgInfo("login.sucesso");
	    return PAG_HOME_URL;
	} catch (Exception exc) {
	    usuarioLogado = null;
	    tentativasInvalidas++;
	    MsgUtil.enviarMsgAviso(exc.getMessage());
	    return PAG_LOGIN_URL;
	}
    }

    public String efetuarLogout() {
	login = null;
	senha = null;
	usuarioLogado = null;
	MsgUtil.enviarMsgInfo("login.logout");
	return PAG_HOME_URL;
    }

    public Boolean deveExibirCaptcha() {
	return tentativasInvalidas > 2;
    }

    public Usuario getUsuarioLogado() {
	return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
	this.usuarioLogado = usuarioLogado;
    }

    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login;
    }

    public String getSenha() {
	return senha;
    }

    public void setSenha(String senha) {
	this.senha = senha;
    }

}
