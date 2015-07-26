package codigoalvo.genericdao;

import java.util.List;

public interface GenericDao<T> {
    T criar(T entity);
    void remover(Object id);
    T buscar(Object id);
    T atualizar(T entity);
    List<T> listar();
}
