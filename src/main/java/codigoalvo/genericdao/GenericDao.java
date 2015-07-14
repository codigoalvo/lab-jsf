package codigoalvo.genericdao;

import java.util.List;

public interface GenericDao<T> {
    T create(T entity);
    void delete(Object id);
    T find(Object id);
    T update(T entity);
    List<T> listAll();
}
