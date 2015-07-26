package codigoalvo.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import codigoalvo.entity.UsuarioTipo;
import codigoalvo.entity.Usuario;
import codigoalvo.repository.UsuarioDao;
import codigoalvo.util.MsgUtil;
import codigoalvo.util.SegurancaUtil;

@ManagedBean(name = "controleUsuario")
@SessionScoped
public class ControleUsuario extends SpringBeanAutowiringSupport implements Serializable {

    private static final long serialVersionUID = 5839585352684182713L;

    @Autowired
    private UsuarioDao<Usuario> usuarioDao;

    @Autowired
    SegurancaUtil segurancaUtil;

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

    public String listar() {
	usuarios = usuarioDao.listar();
	editando = false;
	return "/admin/usuario/listar?faces-redirect=true";
    }

    public String novo() {
	usuario = new Usuario();
	return "editar";
    }

    public String cancelar() {
	return "listar";
    }

    @Transactional
    public String gravar() {
	FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":messages");
	String senhaText = usuario.getSenha();
	if (!segurancaUtil.criptografado(usuario.getSenha())) {
	    usuario.setSenha(segurancaUtil.criptografar(senhaText));
	}
	try {
	    if (usuario.getId() == null) {
		usuarioDao.criar(usuario);
	    } else {
		usuarioDao.atualizar(usuario);
	    }
	    MsgUtil.enviarMsgInfo("gravar.sucesso");
	    return "listar";
	} catch (Throwable exc) {
	    if (usuario.getId() == null) {
		usuario.setSenha(senhaText);
	    }
	    MsgUtil.enviarMsgErro("gravar.erro");
	    return "editar";
	}
    }

    @Transactional
    public String alterar(Usuario usuario) {
	this.usuario = usuario;
	return "editar";
    }

    @Transactional
    public String excluir(Usuario usuario) {
	FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(":messages");
	try {
	    usuarioDao.remover(usuario);
	    MsgUtil.enviarMsgInfo("remover.sucesso");
	} catch (Throwable exc) {
	    MsgUtil.enviarMsgErro("remover.erro");
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
