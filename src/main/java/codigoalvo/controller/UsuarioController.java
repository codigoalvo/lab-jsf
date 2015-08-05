package codigoalvo.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.BeanUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import codigoalvo.entity.Usuario;
import codigoalvo.entity.UsuarioTipo;
import codigoalvo.service.UsuarioService;
import codigoalvo.util.ErrosUtil;
import codigoalvo.util.FacesUtil;
import codigoalvo.util.MsgUtil;

@SessionScoped
@ManagedBean(name = "usuarioController")
public class UsuarioController extends SpringBeanAutowiringSupport implements Serializable {

    private static final long serialVersionUID = 5839585352684182713L;
    private static final Logger LOGGER = Logger.getLogger(UsuarioController.class);

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

    public void novo() {
	Logger.getLogger(UsuarioController.class).debug("novo");
	this.usuario = new Usuario();
    }

    public void alterar(Usuario usuario) {
	Logger.getLogger(UsuarioController.class).debug("alterar");
	this.usuario = new Usuario();
	BeanUtils.copyProperties(usuario, this.usuario);
    }

    public void cancelar() {
	Logger.getLogger(UsuarioController.class).debug("cancelar");
    }

    public void gravar() {
	Logger.getLogger(UsuarioController.class).debug("gravar");
	try {
	    usuario = usuarioService.gravar(usuario);
	    consultar();
	    RequestContext.getCurrentInstance().addCallbackParam("exceptionOccurred", false);
	    MsgUtil.enviarMsgInfo("gravar.sucesso");
	    FacesUtil.atualizarComponentes("messages", "frm-lista");
	} catch (Throwable exc) {
	    String errorMsg = ErrosUtil.getMensagemErro(exc);
	    LOGGER.error(errorMsg);
	    MsgUtil.enviarMsgErro("gravar.erro", errorMsg);
	    RequestContext.getCurrentInstance().addCallbackParam("exceptionOccurred", true);
	}
    }

    public void excluir(Usuario usuario) {
	Logger.getLogger(UsuarioController.class).debug("excluir");
	try {
	    usuarioService.remover(usuario);
	    consultar();
	    MsgUtil.enviarMsgInfo("remover.sucesso");
	    FacesUtil.atualizarComponentes("messages");
	} catch (SQLException exc) {
	    MsgUtil.enviarMsgErro("remover.erro", ErrosUtil.getMensagemErro(exc));
	}
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
