package codigoalvo.controle;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import codigoalvo.modelo.dao.UsuarioDao;
import codigoalvo.modelo.entidades.TipoUsuario;
import codigoalvo.modelo.entidades.Usuario;
import codigoalvo.util.MensagemUtil;
import codigoalvo.util.SegurancaUtil;

@ManagedBean(name = "controleUsuario")
@SessionScoped
public class ControleUsuario extends SpringBeanAutowiringSupport implements Serializable {

    private static final long serialVersionUID = 5839585352684182713L;

    @Autowired
    private UsuarioDao<Usuario> usuarioDao;

    private List<Usuario> usuarios;

    private Usuario usuario;
    private Boolean editando;

    public ControleUsuario() {
	editando = false;
    }

    public TipoUsuario[] getTiposUsuario() {
	return TipoUsuario.values();
    }

    public String listar() {
	usuarios = usuarioDao.listAll();
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
	try {
	    if (usuario.getId() == null) {
		usuario.setSenha(SegurancaUtil.criptografar(senhaText));
		usuarioDao.create(usuario);
	    } else {
		usuarioDao.update(usuario);
	    }
	    MensagemUtil.enviarMensagemInfo("Usuário gravado com sucesso!");
	    return "listar";
	} catch (Throwable exc) {
	    if (usuario.getId() == null) {
		usuario.setSenha(senhaText);
	    }
	    MensagemUtil.enviarMensagemErro("Erro ao gravar o usuário!");
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
	    usuarioDao.delete(usuario);
	    MensagemUtil.enviarMensagemInfo("Usuário removido com sucesso!");
	} catch (Throwable exc) {
	    MensagemUtil.enviarMensagemErro("Erro ao remover o usuário!");
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
