package io.github.zodh.dao;

import io.github.zodh.dao.base.BaseDAO;
import io.github.zodh.dao.base.GenericDAO;
import io.github.zodh.dao.base.HibernateUtil;
import io.github.zodh.entity.Author;


public class AuthorDAO extends BaseDAO<Author, Integer> implements
    GenericDAO<Author, Integer> {

  public AuthorDAO() {
    super(Author.class);
  }

  public void create(Author author) throws Exception {
    beginTransaction();
    var hql = "insert into authors (name, fantasy_name) values (?,?)";
    var statement = connection.prepareStatement(hql);
    try {
      statement.setString(1, author.getName());
      statement.setString(2, author.getFantasyName());
      statement.execute();
      statement.close();
    } catch (Exception exception) {
      rollbackTransaction();
      throw new Exception(
          "Error trying to save a new author in the database. " + exception.getMessage());
    } finally {
      endTransaction();
    }
  }

  public Author findById(Integer id) throws Exception {
    try {
      beginTransaction();
      return HibernateUtil.getSession().get(Author.class, id);
    } catch (Exception exception) {
      rollbackTransaction();
      throw new Exception(
          "Error trying to find author by id. Error: " + exception.getMessage());
    } finally {
      endTransaction();
    }
  }
}
