package io.github.zodh.dao.base;

import static java.util.Objects.isNull;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

  private static final ThreadLocal<Session> threadLocal = new ThreadLocal<>();
  private static final SessionFactory sessionFactory = new Configuration().configure()
      .buildSessionFactory();

  public static Session getSession() {
    if (isNull(threadLocal.get())) {
      threadLocal.set(sessionFactory.openSession());
    }
    return threadLocal.get();
  }

  public static void closeSession() {
    threadLocal.remove();
  }

  public static void beginTransaction() {
    getSession().beginTransaction();
    if (Boolean.FALSE.equals(isTransactionActive())) {
      getSession().beginTransaction();
    }
  }

  public static void commitTransaction() {
    try {
      getSession().getTransaction().commit();
    } catch (Exception exception) {
      getSession().flush();
      getSession().clear();
      getSession().getTransaction().commit();
    }
  }

  public static void rollBackTransaction() {
    getSession().getTransaction().rollback();
  }

  private static Boolean isTransactionActive() {
    return getSession().getTransaction().isActive();
  }
}
