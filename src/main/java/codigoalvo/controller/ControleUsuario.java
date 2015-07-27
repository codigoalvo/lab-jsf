package codigoalvo.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import codigoalvo.entity.Usuario;
import codigoalvo.entity.UsuarioTipo;
import codigoalvo.service.UsuarioService;
import codigoalvo.util.ErrosUtil;
import codigoalvo.util.MsgUtil;

@ManagedBean(name = "controleUsuario")
@SessionScoped
public class ControleUsuario extends SpringBeanAutowiringSupport implements Serializable {

    private static final long serialVersionUID = 5839585352684182713L;

    @Autowired
    private UsuarioService usuarioService;

    private List<Usuario> usuarios;

    private Usuario usuario;
    private Boolean editando;

    private SelectItem[] tiposUsuario;

    public ControleUsuario() {
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
	return "editar";
    }

    public String alterar(Usuario usuario) {
	this.usuario = usuario;
	return "editar";
    }

    public String cancelar() {
	return "listar";
    }

    public String listar() {
	usuarios = usuarioService.listar();
	editando = false;
	return "/admin/usuario/listar?faces-redirect=true";
    }

    public String gravar() {
	FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":messages");
	try {
	    usuario = usuarioService.gravar(usuario);
	    MsgUtil.enviarMsgInfo("gravar.sucesso");
	    return "listar";
	} catch (SQLException exc) {
	    MsgUtil.enviarMsgErro("gravar.erro", ErrosUtil.getMensagemErro(exc));
	    return "editar";
	}
    }

    public String excluir(Usuario usuario) {
	FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":messages");
	try {
	    usuarioService.remover(usuario);
	    MsgUtil.enviarMsgInfo("remover.sucesso");
	} catch (SQLException exc) {
	    MsgUtil.enviarMsgErro("remover.erro", ErrosUtil.getMensagemErro(exc));
	}
	return "listar";
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
