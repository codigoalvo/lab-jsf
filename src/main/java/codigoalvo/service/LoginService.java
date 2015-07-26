package codigoalvo.service;

import java.util.Date;
import javax.security.auth.login.LoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import codigoalvo.entity.Usuario;
import codigoalvo.repository.UsuarioDao;
import codigoalvo.util.MsgParamUtil;
import codigoalvo.util.SegurancaUtil;

@Service
public class LoginService {

    public static final Integer MAXIMO_TENTATIVAS_LOGIN = 5;

    @Autowired
    private UsuarioDao<Usuario> usuarioDao;

    @Autowired
    SegurancaUtil segurancaUtil;

    @Transactional
    public Usuario efetuarLogin(String login, String senha)
	throws LoginException {

	Usuario usuario = usuarioDao.buscarPorLogin(login);
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
