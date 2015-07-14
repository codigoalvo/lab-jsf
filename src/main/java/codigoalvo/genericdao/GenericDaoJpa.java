package codigoalvo.genericdao;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public T create(final T entity) {
	this.entityManager.persist(entity);
	return entity;
    }

    @Override
    @Transactional
    public void delete(final Object id) {
	this.entityManager.remove(this.entityManager.getReference(getTypeClass(), id));
    }

    @Override
    @Transactional
    public T find(final Object id) {
	return this.entityManager.find(getTypeClass(), id);
    }

    @Override
    @Transactional
    public T update(final T entity) {
	return this.entityManager.merge(entity);
    }

    @Override
    @Transactional
    @SuppressWarnings("unchecked")
    public List<T> listAll() {
	return entityManager.createQuery(("FROM " + getTypeClass().getName())).getResultList();
    }

}
