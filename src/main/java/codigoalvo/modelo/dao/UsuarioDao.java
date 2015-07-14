package codigoalvo.modelo.dao;

import codigoalvo.genericdao.GenericDao;
import codigoalvo.modelo.entidades.Usuario;

public interface UsuarioDao<T> extends GenericDao<T> {

    public Usuario localizaPorLogin(String login);

}
