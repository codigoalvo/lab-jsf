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
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import codigoalvo.entity.Usuario;
import codigoalvo.entity.UsuarioTipo;
import codigoalvo.service.UsuarioService;
import codigoalvo.util.ErrosUtil;
import codigoalvo.util.MsgUtil;

@SessionScoped
@ManagedBean(name = "controleUsuario")
public class ControleUsuario extends SpringBeanAutowiringSupport implements Serializable {

    private static final long serialVersionUID = 5839585352684182713L;
    private static final String LISTAR = null; // "listar?faces-redirect=true";
    private static final String EDITAR = null; // "editar?faces-redirect=true";

    @Inject
    private transient UsuarioService usuarioService;

    private List<Usuario> usuarios;

    private Usuario usuario;
    private Boolean editando;

    private SelectItem[] tiposUsuario;

    public ControleUsuario() {
	Logger.getLogger(ControleUsuario.class).debug("####################  construct  ####################");
	usuario = new Usuario();
	editando = false;
	carregaTiposUsuario();
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
	return tiposUsuario;
    }

    public String listar() {
	Logger.getLogger(ControleUsuario.class).debug("listar");
	editando = false;
	return "/admin/usuario/listar?faces-redirect=true";
    }

    public void consultar() {
	Logger.getLogger(ControleUsuario.class).debug("consultar");
	usuarios = usuarioService.listar();
    }

    public String novo() {
	Logger.getLogger(ControleUsuario.class).debug("novo");
	usuario = new Usuario();
	editando = true;
	return EDITAR;
    }

    public String alterar(Usuario usuario) {
	Logger.getLogger(ControleUsuario.class).debug("alterar");
	this.usuario = usuario;
	editando = true;
	return EDITAR;
    }

    public String cancelar() {
	Logger.getLogger(ControleUsuario.class).debug("cancelar");
	editando = false;
	return LISTAR;
    }

    public void gravar() {
	Logger.getLogger(ControleUsuario.class).debug("gravar");
	FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":msgs-dialog");
	try {
	    usuario = usuarioService.gravar(usuario);
	    MsgUtil.enviarMsgInfo("gravar.sucesso");
	    editando = false;
	    RequestContext.getCurrentInstance().execute("PF('dialogEditar').hide()");
//	    return LISTAR;
	} catch (SQLException exc) {
	    MsgUtil.enviarMsgErro("gravar.erro", ErrosUtil.getMensagemErro(exc));
//	    return EDITAR;
	}
    }

    public String excluir(Usuario usuario) {
	Logger.getLogger(ControleUsuario.class).debug("excluir");
	FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":messages");
	try {
	    usuarioService.remover(usuario);
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

    public Boolean getEditando() {
	return editando;
    }

    public void setEditando(Boolean editando) {
	this.editando = editando;
    }

    public List<Usuario> getUsuarios() {
	return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
	this.usuarios = usuarios;
    }

}
