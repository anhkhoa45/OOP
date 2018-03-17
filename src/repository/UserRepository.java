package repository;

import model.User;

import javax.persistence.*;
import javax.ws.rs.BadRequestException;
import java.util.List;

public class UserRepository extends AbstractRepository<User> {
    @Override
    public User find(int userId) {
        return em.find(User.class, userId);
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    @Override
    public List<User> paginate(int offset, int limit) {
        return null;
    }

    @Override
    public User save(User user) {
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        try {
            em.persist(user);
            tran.commit();
            return user;
        } catch (Exception e) {
            tran.rollback();
            throw new BadRequestException();
        }
    }

    @Override
    public User update(User User, int userId) {
        return null;
    }

    @Override
    public User delete(int userId) {
        return null;
    }

    public User findByEmail(String email){
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = '" + email + "'", User.class);
            return query.getResultList().get(0);
        } catch (Exception e) {
            return null;
        }
    }
}
