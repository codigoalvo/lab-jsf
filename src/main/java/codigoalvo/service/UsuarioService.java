package codigoalvo.service;

import java.sql.SQLException;
import java.util.List;
import codigoalvo.entity.Usuario;


public interface UsuarioService {

    Usuario gravar(Usuario entity) throws SQLException;
    void remover(Object id) throws SQLException;
    Usuario buscar(Object id);
    List<Usuario> listar();
    public Usuario buscarPorLogin(String login);
    public Usuario buscarPorEmail(String email);

}
