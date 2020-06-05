package dao;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DAO<T> {

//  Collection<T> getAll();

  List<T> getList(String choice);

  Optional<T> get(int id);

  int getID(String email);

  boolean check(String condition);

  boolean executeSQL(String sb);
}