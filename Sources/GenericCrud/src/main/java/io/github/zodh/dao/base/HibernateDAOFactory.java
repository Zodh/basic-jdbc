package io.github.zodh.dao.base;

import io.github.zodh.dao.AuthorDAO;

public class HibernateDAOFactory extends DAOFactory {

  @Override
  public AuthorDAO getAuthorDAO() {
    return new AuthorDAO();
  }

}
