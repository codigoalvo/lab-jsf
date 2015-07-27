package codigoalvo.repository;

import org.springframework.stereotype.Repository;
import codigoalvo.entity.Usuario;
import codigoalvo.genericdao.GenericDaoJpa;

@Repository("usuarioDaoJpa")
public class UsuarioDaoJpa extends GenericDaoJpa<Usuario> implements UsuarioDao {

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
