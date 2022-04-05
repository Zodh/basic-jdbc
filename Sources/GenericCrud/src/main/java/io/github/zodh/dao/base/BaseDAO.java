package io.github.zodh.dao.base;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;

public class BaseDAO<T, Type extends Serializable> implements GenericDAO<T, Type> {

  private final Class<T> persistentClass;
  protected Connection connection;

  public BaseDAO(Class<T> persistentClass) {
    super();
    this.persistentClass = persistentClass;
  }

  public void openConn() throws Exception {
    try {
      if (nonNull(connection)){
        closeConn();
        connection = null;
      }
      Class.forName("org.h2.Driver");
      this.connection = DriverManager
          .getConnection(
              "jdbc:h2:C:\\Users\\felipe_santos\\Desktop\\livraria-amazonia-develop\\Sources\\GenericCrud\\database\\test.h2.db",
              "", "");
    } catch (Exception e) {
      throw new Exception("Error trying to open connection. Error: " + e.getMessage());
    }
  }

  public void closeConn() throws Exception {
    try {
      if (nonNull(this.connection)){
        this.connection.close();
      }
    } catch (Exception exception) {
      throw new Exception("Error trying to close connection");
    }
  }

  @Override
  public void delete(T entity) throws Exception {
    try {
      beginTransaction();
      HibernateUtil.getSession().delete(entity);
    } catch (Exception exception) {
      rollbackTransaction();
      throw new Exception("Error trying to delete an object." + exception.getMessage());
    } finally {
      endTransaction();
    }
  }

  @Override
  public List<T> listAll() throws Exception {
    try {
      beginTransaction();
      CriteriaQuery<T> criteriaQuery = HibernateUtil.getSession().getCriteriaBuilder()
          .createQuery(persistentClass);
      criteriaQuery.from(persistentClass);
      return HibernateUtil.getSession().createQuery(criteriaQuery).getResultList();
    } catch (Exception exception) {
      throw new Exception("Error trying to list objects." + exception.getMessage());
    } finally {
      endTransaction();
    }
  }

  @Override
  public void update(T entity) throws Exception {
    try {
      beginTransaction();
      HibernateUtil.getSession().update(entity);
      commitTransaction();
    } catch (Exception exception) {
      rollbackTransaction();
      throw new Exception("Error trying to update fields. " + exception.getMessage());
    } finally {
      endTransaction();
    }
  }

  public void beginTransaction() throws Exception {
    if (isNull(connection)){
      openConn();
    }
    HibernateUtil.beginTransaction();
  }

  public void rollbackTransaction() {
    HibernateUtil.rollBackTransaction();
  }

  public void commitTransaction() {
    HibernateUtil.commitTransaction();
  }

  protected void endTransaction() {
    HibernateUtil.closeSession();
  }
}
