package codigoalvo.controle;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import codigoalvo.modelo.dao.UsuarioDao;
import codigoalvo.modelo.entidades.Usuario;
import codigoalvo.util.MensagemUtil;
import codigoalvo.util.SegurancaUtil;

@ManagedBean(name = "controleLogin")
@SessionScoped
public class ControleLogin extends SpringBeanAutowiringSupport implements Serializable {

    private static final long serialVersionUID = 3670261714704666556L;

    public static int MAXIMO_TENTATIVAS_LOGIN = 5;

    @Autowired
    private UsuarioDao usuarioDao;

    private Usuario usuarioLogado;
    private Integer tentativasInvalidas;

    private String login;

    private String senha;

    public ControleLogin() {
	tentativasInvalidas = 0;
    }

    public String paginaLogin() {
	return "/login";
    }

    @Transactional
    public String efetuarLogin() {
	FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":messages");
	Usuario usuario = usuarioDao.localizaPorLogin(login);
	if (usuario == null || usuario.getId() == null) {
	    MensagemUtil.enviarMensagemAviso("Usuário ou senha inválidos!");
	    tentativasInvalidas++;
	    return "/login";
	} else {
	    if (!usuario.getAtivo()) {
		MensagemUtil.enviarMensagemAviso("Acesso negado - Usuário desativado - Entre em contato com o suporte");
		return "/login";
	    } else if (usuario.getSenha().equals(SegurancaUtil.criptografar(senha))) {
		usuario.setDataUltimoLogin(new Date());
		usuario.setTentativasLoginInvalido(0);
		usuario.setDataUltimaFalhaLogin(null);
		usuarioDao.update(usuario);
		tentativasInvalidas = 0;
		usuarioLogado = usuario;
		MensagemUtil.enviarMensagemInfo("Login efetuado com sucesso!");
		return "/index";
	    } else if (usuario.getTentativasLoginInvalido() >= MAXIMO_TENTATIVAS_LOGIN) {
		usuario.setDataUltimaFalhaLogin(new Date());
		usuario.setAtivo(false);
		usuarioDao.update(usuario);
		tentativasInvalidas++;
		MensagemUtil.enviarMensagemAviso("Senha inválida! - Usuário desativado por excesso de tentativas de login!");
		return "/login";
	    } else {
		usuario.setTentativasLoginInvalido(usuario.getTentativasLoginInvalido()+1);
		usuario.setDataUltimaFalhaLogin(new Date());
		usuarioDao.update(usuario);
		tentativasInvalidas++;
		MensagemUtil.enviarMensagemAviso("Senha inválida! - Tentativa " + usuario.getTentativasLoginInvalido() + " de "
		        + MAXIMO_TENTATIVAS_LOGIN);
		return "/login";
	    }
	}
    }

    public String efetuarLogout() {
	login = null;
	senha = null;
	usuarioLogado = null;
	return "/index";
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
