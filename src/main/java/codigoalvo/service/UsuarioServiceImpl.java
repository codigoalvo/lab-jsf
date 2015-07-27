package codigoalvo.service;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import codigoalvo.entity.Usuario;
import codigoalvo.repository.UsuarioDao;
import codigoalvo.util.SegurancaUtil;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    SegurancaUtil segurancaUtil;

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
	    throw new SQLException(exc);
	}

    }

    @Override
    public void remover(Object id)
	throws SQLException {
	try {
	    usuarioDao.remover(id);
	} catch (Throwable exc) {
	    throw new SQLException(exc);
	}

    }

    @Override
    public Usuario buscar(Object id) {
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
