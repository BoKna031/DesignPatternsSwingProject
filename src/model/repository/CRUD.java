package model.repository;

public interface CRUD<ID, T> {
    T create(T entity);

    T read(ID id);

    T update(T entity);

    T delete(ID id);
}
