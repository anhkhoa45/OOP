package repository;

import util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public abstract class AbstractRepository<T>{
    @PersistenceContext
    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

    public abstract T find(int id);
    public abstract List<T> findAll();
    public abstract List<T> paginate(int offset, int limit);
    public abstract T save(T ojb);
    public abstract T update(T ojb, int id);
    public abstract T delete(int id);
}
