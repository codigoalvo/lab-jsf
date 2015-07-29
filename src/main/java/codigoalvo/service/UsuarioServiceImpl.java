package codigoalvo.service;

import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;
import codigoalvo.entity.Usuario;
import codigoalvo.repository.UsuarioDao;
import codigoalvo.util.SegurancaUtil;

@Named
@RequestScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    private UsuarioDao usuarioDao;

    @Inject
    SegurancaUtil segurancaUtil;

    public UsuarioServiceImpl() {
	Logger.getLogger(UsuarioServiceImpl.class).debug("####################  construct  ####################");
    }

    @Override
    public Usuario gravar(Usuario usuario)
	throws SQLException {

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
	    return usuario;
	} catch (Throwable exc) {
	    if (usuario.getId() == null) {
		usuario.setSenha(senhaText);
	    }
	    throw exc;
	}

    }

    @Override
    public void remover(Usuario usuario)
	throws SQLException {
	try {
	    usuarioDao.remover(usuario.getId());
	} catch (Throwable exc) {
	    throw new SQLException(exc);
	}

    }

    @Override
    public void removerPorId(Integer id)
        throws SQLException {
	try {
	    usuarioDao.remover(id);
	} catch (Throwable exc) {
	    throw new SQLException(exc);
	}
    }

    @Override
    public Usuario buscar(Integer id) {
	return usuarioDao.buscar(id);
    }

    @Override
    public List<Usuario> listar() {
	return usuarioDao.listar();
    }

    @Override
    public Usuario buscarPorLogin(String login) {
	Usuario usuario = null;
	try {
	    usuario = usuarioDao.buscarPorLogin(login);
	} catch (NoResultException nre) {
	    Logger.getLogger(UsuarioServiceImpl.class).debug("Usuario não encontrado (login): " + login);
	}
	return usuario;
    }

    @Override
    public Usuario buscarPorEmail(String email) {
	Usuario usuario = null;
	try {
	    usuario = usuarioDao.buscarPorEmail(email);
	} catch (NoResultException nre) {
	    Logger.getLogger(UsuarioServiceImpl.class).debug("Usuario não encontrado (email): " + email);
	}
	return usuario;
    }

}
