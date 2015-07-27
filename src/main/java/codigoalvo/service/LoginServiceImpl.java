package codigoalvo.service;

import java.util.Date;
import javax.persistence.NoResultException;
import javax.security.auth.login.LoginException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import codigoalvo.entity.Usuario;
import codigoalvo.repository.UsuarioDao;
import codigoalvo.util.MsgParamUtil;
import codigoalvo.util.SegurancaUtil;

@Service
public class LoginServiceImpl implements LoginService {

    public static final Integer MAXIMO_TENTATIVAS_LOGIN = 5;

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    SegurancaUtil segurancaUtil;

    @Override
    @Transactional
    public Usuario efetuarLogin(String login, String senha)
	throws LoginException {
	Usuario usuario = null;
	try {
	    usuario = usuarioDao.buscarPorLogin(login);
	} catch (NoResultException nre) {
	    Logger.getLogger(LoginServiceImpl.class).debug("Usuario não encontrado (login): " + login);
	}
	if (usuario == null || usuario.getId() == null) {
	    throw new LoginException("login.invalido");
	} else {
	    if (!usuario.getAtivo()) {
		throw new LoginException("login.usuarioDesativado");
	    } else if (usuario.getSenha().equals(segurancaUtil.criptografar(senha))) {
		usuario.setDataUltimoLogin(new Date());
		usuario.setTentativasLoginInvalido(0);
		usuario.setDataUltimaFalhaLogin(null);
		usuarioDao.atualizar(usuario);
		return usuario;
	    } else if (usuario.getTentativasLoginInvalido() >= MAXIMO_TENTATIVAS_LOGIN) {
		usuario.setDataUltimaFalhaLogin(new Date());
		usuario.setAtivo(false);
		usuarioDao.atualizar(usuario);
		throw new LoginException("login.senhaDesativada");
	    } else {
		usuario.setTentativasLoginInvalido(usuario.getTentativasLoginInvalido() + 1);
		usuario.setDataUltimaFalhaLogin(new Date());
		usuarioDao.atualizar(usuario);
		String msg = "login.senhaInvalida";
		msg += MsgParamUtil.buildParams(usuario.getTentativasLoginInvalido().toString(), MAXIMO_TENTATIVAS_LOGIN.toString());
		throw new LoginException(msg);
	    }
	}
    }

}
