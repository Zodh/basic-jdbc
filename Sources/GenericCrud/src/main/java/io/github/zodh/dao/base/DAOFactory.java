package io.github.zodh.dao.base;

import io.github.zodh.dao.AuthorDAO;
import java.lang.reflect.InvocationTargetException;

public abstract class DAOFactory {

  private static final Class<HibernateDAOFactory> FACTORY_CLASS = HibernateDAOFactory.class;

  public static DAOFactory getFactory() throws Exception {
    try {
      return FACTORY_CLASS.getDeclaredConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new Exception("Error trying to create DAO.");
    }
  }

  public abstract AuthorDAO getAuthorDAO();
}
