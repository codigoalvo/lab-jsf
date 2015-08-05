package codigoalvo.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import codigoalvo.entity.Usuario;
import codigoalvo.entity.UsuarioTipo;
import codigoalvo.service.UsuarioService;
import codigoalvo.util.ErrosUtil;
import codigoalvo.util.MsgUtil;

@SessionScoped
@ManagedBean(name = "usuarioController")
public class UsuarioController extends SpringBeanAutowiringSupport implements Serializable {

    private static final long serialVersionUID = 5839585352684182713L;
    private static final Logger LOGGER = Logger.getLogger(UsuarioController.class);
    private static final String LISTAR = "listar?faces-redirect=true";
    private static final String EDITAR = null; // "editar?faces-redirect=true";

    @Inject
    private transient UsuarioService usuarioService;

    private List<Usuario> usuarios;

    private Usuario usuario;

    private SelectItem[] tiposUsuario;

    public UsuarioController() {
	LOGGER.debug("####################  construct  ####################");
	usuario = new Usuario();
    }

    private void carregaTiposUsuario() {
	List<SelectItem> tipos = new ArrayList<>();
	SelectItem selecione = new SelectItem("", MsgUtil.i18nMsg("selectitem.selecione"));
	selecione.setNoSelectionOption(true);
	tipos.add(selecione);
	for (UsuarioTipo tipo : UsuarioTipo.values()) {
	    tipos.add(new SelectItem(tipo, tipo.getDescricao()));
	}
	tiposUsuario = tipos.toArray(new SelectItem[0]);
    }

    public SelectItem[] getTiposUsuario() {
	if (tiposUsuario == null) {
	    carregaTiposUsuario();
	}
	return tiposUsuario;
    }

    public String listar() {
	Logger.getLogger(UsuarioController.class).debug("listar");
	return "/admin/usuario/listar?faces-redirect=true";
    }

    public void consultar() {
	Logger.getLogger(UsuarioController.class).debug("consultar");
	usuarios = usuarioService.listar();
    }

    public String novo() {
	Logger.getLogger(UsuarioController.class).debug("novo");
	this.usuario = new Usuario();
	return EDITAR;
    }

    public String alterar(Usuario usuario) {
	Logger.getLogger(UsuarioController.class).debug("alterar");
	this.usuario = usuario;
	return EDITAR;
    }

    public String cancelar() {
	Logger.getLogger(UsuarioController.class).debug("cancelar");
	return LISTAR;
    }

    public String gravar() {
	Logger.getLogger(UsuarioController.class).debug("gravar");
	try {
	    usuario = usuarioService.gravar(usuario);
	    FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":messages");
	    MsgUtil.enviarMsgInfo("gravar.sucesso");
	    RequestContext.getCurrentInstance().addCallbackParam("exceptionOccurred", false);
	    return LISTAR;
	} catch (Throwable exc) {
	    String errorMsg = ErrosUtil.getMensagemErro(exc);
	    LOGGER.error(errorMsg);
	    MsgUtil.enviarMsgErro("gravar.erro", errorMsg);
	    RequestContext.getCurrentInstance().addCallbackParam("exceptionOccurred", true);
	    return EDITAR;
	}
    }

    public void aoFechar(CloseEvent event) {
	Logger.getLogger(UsuarioController.class).debug("aoFechar");
	consultar();
    }

    public String excluir(Usuario usuario) {
	Logger.getLogger(UsuarioController.class).debug("excluir");
	try {
	    usuarioService.remover(usuario);
	    FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":messages");
	    MsgUtil.enviarMsgInfo("remover.sucesso");
	} catch (SQLException exc) {
	    MsgUtil.enviarMsgErro("remover.erro", ErrosUtil.getMensagemErro(exc));
	}
	return LISTAR;
    }

    public Usuario getUsuario() {
	return usuario;
    }

    public void setUsuario(Usuario usuario) {
	this.usuario = usuario;
    }

    public List<Usuario> getUsuarios() {
	return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
	this.usuarios = usuarios;
    }

}
