package codigoalvo.repository;

import codigoalvo.entity.Usuario;
import codigoalvo.genericdao.GenericDao;

public interface UsuarioDao<T> extends GenericDao<T> {

    public Usuario buscarPorLogin(String login);

}
