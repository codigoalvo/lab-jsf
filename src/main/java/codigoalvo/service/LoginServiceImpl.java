package codigoalvo.service;

import java.util.Date;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.security.auth.login.LoginException;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import codigoalvo.entity.Usuario;
import codigoalvo.repository.UsuarioDao;
import codigoalvo.util.MsgParamUtil;
import codigoalvo.util.SegurancaUtil;

@Named
@RequestScoped
public class LoginServiceImpl implements LoginService {

    public static final Integer MAXIMO_TENTATIVAS_LOGIN = 5;
    private static final Logger LOGGER = Logger.getLogger(LoginServiceImpl.class);

    @Inject
    private UsuarioDao usuarioDao;

    @Inject
    SegurancaUtil segurancaUtil;

    public LoginServiceImpl() {
	LOGGER.debug("####################  construct  ####################");
    }

    @Override
    @Transactional
    public Usuario efetuarLogin(String login, String senha)
	throws LoginException {
	Usuario usuario = null;
	try {
	    usuario = usuarioDao.buscarPorLogin(login);
	} catch (NoResultException nre) {
	    LOGGER.debug("Usuario não encontrado (login): " + login);
	}
	if (usuario == null || usuario.getId() == null) {
	    LOGGER.debug("login.invalido");
	    throw new LoginException("login.invalido");
	} else {
	    if (!usuario.getAtivo()) {
		LOGGER.debug("login.usuarioDesativado");
		throw new LoginException("login.usuarioDesativado");
	    } else if (usuario.getSenha().equals(segurancaUtil.criptografar(senha))) {
		usuario.setDataUltimoLogin(new Date());
		usuario.setTentativasLoginInvalido(0);
		usuario.setDataUltimaFalhaLogin(null);
		LOGGER.debug("login.sucesso");
		usuarioDao.atualizar(usuario);
		return usuario;
	    } else if (usuario.getTentativasLoginInvalido() >= MAXIMO_TENTATIVAS_LOGIN) {
		usuario.setDataUltimaFalhaLogin(new Date());
		usuario.setAtivo(false);
		usuarioDao.atualizar(usuario);
		LOGGER.debug("login.senhaDesativada");
		throw new LoginException("login.senhaDesativada");
	    } else {
		usuario.setTentativasLoginInvalido(usuario.getTentativasLoginInvalido() + 1);
		usuario.setDataUltimaFalhaLogin(new Date());
		usuarioDao.atualizar(usuario);
		String msg = "login.senhaInvalida";
		msg += MsgParamUtil.buildParams(usuario.getTentativasLoginInvalido().toString(), MAXIMO_TENTATIVAS_LOGIN.toString());
		LOGGER.debug(msg);
		throw new LoginException(msg);
	    }
	}
    }

}
