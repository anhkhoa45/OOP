package repository;

import java.util.List;

public interface RepositoryInterface<T> {
    T find(int id);
    List<T> findAll();
    List<T> paginate(int offset, int limit);
    T save(T ojb);
    T update(T ojb, int id);
    T delete(int id);
}
