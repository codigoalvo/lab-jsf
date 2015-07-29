package codigoalvo.genericdao;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

public abstract class GenericDaoJpa<T> implements GenericDao<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    private Class<T> getTypeClass() {
	return (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    @Transactional
    public T criar(final T entity) {
	this.entityManager.persist(entity);
	return entity;
    }

    @Override
    @Transactional
    public void remover(final Object id) {
	this.entityManager.remove(this.entityManager.getReference(getTypeClass(), id));
    }

    @Override
    public T buscar(final Object id) {
	return this.entityManager.find(getTypeClass(), id);
    }

    @Override
    @Transactional
    public T atualizar(final T entity) {
	return this.entityManager.merge(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> listar() {
	return entityManager.createQuery(("FROM " + getTypeClass().getName())).getResultList();
    }

    @Override
    public T buscarPor(String campo, Object valor) {
	return buscarPor(campo, valor, "=", false);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T buscarPor(String campo, Object valor, String comparador, boolean caseSensitive) {
	StringBuilder jpql = new StringBuilder("from ");
	jpql.append(getTypeClass().getName()).append(" where ");
	if (!caseSensitive) {
	    jpql.append("upper(");
	}
	jpql.append(campo);
	if (!caseSensitive) {
	    jpql.append(")");
	}
	jpql.append(" ").append(comparador).append(" ");
	jpql.append(":valor");
	Query query = entityManager.createQuery(jpql.toString());
	if (!caseSensitive) {
	    query.setParameter("valor", valor.toString().toUpperCase());
	} else {
	    query.setParameter("valor", valor);
	}
	T result = null;
	result = (T)query.getSingleResult();
	if (result == null) {
	    Logger.getLogger(GenericDaoJpa.class).debug(getTypeClass().getName()+" (buscarPor="+campo+"): "+valor+" ! Not Found !");
	}
	return result;
    }

    protected boolean emptyOrNull(String valor) {
	Logger.getLogger(GenericDaoJpa.class).debug(valor);
	return (valor == null  ||  valor.trim().isEmpty());
    }

}
