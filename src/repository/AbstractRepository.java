package repository;

import util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractRepository<T> implements RepositoryInterface<T> {
    @PersistenceContext
    protected EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
}
