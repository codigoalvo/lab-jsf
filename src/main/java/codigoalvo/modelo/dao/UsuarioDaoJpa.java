package codigoalvo.modelo.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import codigoalvo.genericdao.GenericDaoJpa;
import codigoalvo.modelo.entidades.Usuario;

@Component("usuarioDaoJpa")
public class UsuarioDaoJpa extends GenericDaoJpa<Usuario> implements UsuarioDao<Usuario> {

    @Override
    @Transactional
    public Usuario localizaPorLogin(String login) {
	String jpql = "from Usuario where upper(login) = :login";
	Query query = entityManager.createQuery(jpql);
	query.setParameter("login", login.toUpperCase());
	Usuario result = null;
	try {
	    result = (Usuario)query.getSingleResult();
        } catch (NoResultException nre) {
	    System.out.println("Usuario não encontrado: "+login);
        }
	return result;
    }

}
