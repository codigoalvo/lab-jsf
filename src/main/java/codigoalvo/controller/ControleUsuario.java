package codigoalvo.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import codigoalvo.entity.Usuario;
import codigoalvo.entity.UsuarioTipo;
import codigoalvo.service.UsuarioService;
import codigoalvo.util.ErrosUtil;
import codigoalvo.util.MsgUtil;

@Named
@Scope("session")
@ManagedBean(name = "controleUsuario")
public class ControleUsuario implements Serializable {

    private static final long serialVersionUID = 5839585352684182713L;
    private static final String LISTAR = "listar?faces-redirect=true";
    private static final String EDITAR = "editar?faces-redirect=true";

    @Inject
    private transient UsuarioService usuarioService;

    private List<Usuario> usuarios;

    private Usuario usuario;
    private Boolean editando;

    private SelectItem[] tiposUsuario;

    public ControleUsuario() {
	Logger.getLogger(ControleUsuario.class).debug("construct");
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

    public String novo() {
	usuario = new Usuario();
	return EDITAR;
    }

    public String alterar(Usuario usuario) {
	Logger.getLogger(ControleUsuario.class).debug("alterar");
	this.usuario = usuario;
	return EDITAR;
    }

    public String cancelar() {
	Logger.getLogger(ControleUsuario.class).debug("cancelar");
	return LISTAR;
    }

    public String listar() {
	Logger.getLogger(ControleUsuario.class).debug("listar");
	usuarios = usuarioService.listar();
	editando = false;
	return "/admin/usuario/listar?faces-redirect=true";
    }

    public String gravar() {
	Logger.getLogger(ControleUsuario.class).debug("gravar");
	FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":messages");
	try {
	    usuario = usuarioService.gravar(usuario);
	    MsgUtil.enviarMsgInfo("gravar.sucesso");
	    return LISTAR;
	} catch (SQLException exc) {
	    MsgUtil.enviarMsgErro("gravar.erro", ErrosUtil.getMensagemErro(exc));
	    return EDITAR;
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
