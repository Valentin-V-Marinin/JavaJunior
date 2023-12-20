package seminar4.repository;

import java.util.List;

public interface Repository<T, TId> {

    void add(T item);
    void update(T item);
    void delete(T item);
    T getById(TId id);
    void addAll(List<T> list);

}
