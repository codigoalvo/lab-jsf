package codigoalvo.repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import codigoalvo.entity.Usuario;
import codigoalvo.genericdao.GenericDaoJpa;

@Repository("usuarioDaoJpa")
public class UsuarioDaoJpa extends GenericDaoJpa<Usuario> implements UsuarioDao<Usuario> {

    private static final Logger LOGGER = Logger.getLogger(UsuarioDaoJpa.class);

    @Override
    @Transactional
    public Usuario buscarPorLogin(String login) {
	String jpql = "from Usuario where upper(login) = :login";
	Query query = entityManager.createQuery(jpql);
	query.setParameter("login", login.toUpperCase());
	Usuario result = null;
	try {
	    result = (Usuario)query.getSingleResult();
	} catch (NoResultException nre) {
	    LOGGER.debug("Usuario não encontrado: " + login);
	}
	return result;
    }

}
