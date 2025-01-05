package Infra.Repository;
import java.util.*;
import java.util.function.Predicate;

public interface IRepository<T> {
    List<T> getList();
    Map<Integer, T> getMap();
    boolean insert(T entity);
    boolean delete(int id);
    Optional<T> getById(int id);
    List<T> whereAsList(Predicate<T> predicate);
    Map<Integer, T> whereAsMap(Predicate<T> predicate);
    Map<Integer, T> sortAsMap(Predicate<T> predicate);
    List<T> Search(Predicate<T> predicate);
    T update(T entity);
    Optional<T> FirstOrDefault(Predicate<T> predicate);
}
