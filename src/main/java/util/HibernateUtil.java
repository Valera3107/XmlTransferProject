package util;

import lombok.extern.java.Log;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.logging.Level;

@Log
public class HibernateUtil {
  private static final SessionFactory sessionFactory = buildSessionFactory();

  private HibernateUtil() {
  }

  private static SessionFactory buildSessionFactory() {
    try {
      Configuration configuration = new Configuration();
      configuration.configure("hibernate.cfg.xml");

      ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
        .applySettings(configuration.getProperties()).build();

      log.info("Created session factory;");
      return configuration.buildSessionFactory(serviceRegistry);
    } catch (Exception e) {
      log.log(Level.WARNING, "Failed to create session factory;");
      throw new ExceptionInInitializerError(e);
    }
  }

  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public static void shutdown() {
    sessionFactory.close();
  }
}
