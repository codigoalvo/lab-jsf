package codigoalvo.repository;

import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import org.apache.log4j.Logger;
import codigoalvo.entity.Usuario;
import codigoalvo.genericdao.GenericDaoJpa;

@Named
@RequestScoped
public class UsuarioDaoJpa extends GenericDaoJpa<Usuario> implements UsuarioDao {

    public UsuarioDaoJpa() {
	Logger.getLogger(UsuarioDaoJpa.class).debug("####################  construct  ####################");
    }

    @Override
    public Usuario buscarPorEmail(String email) {
	if (emptyOrNull(email)) {
	    return null;
	}
	return buscarPor("email", email.trim());
    }

    @Override
    public Usuario buscarPorLogin(String login) {
	if (emptyOrNull(login)) {
	    return null;
	}
	return buscarPor("login", login);
    }

}
