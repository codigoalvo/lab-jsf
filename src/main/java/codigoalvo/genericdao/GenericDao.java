package codigoalvo.genericdao;

import java.util.List;

public interface GenericDao<T> {

    T criar(T entity);
    void remover(Object id);
    T buscar(Object id);
    T atualizar(T entity);
    List<T> listar();
    T buscarPor(String campo, Object valor);
    T buscarPor(String campo, Object valor, String comparador, boolean caseSensitive);

}
