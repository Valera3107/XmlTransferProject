package service;

import lombok.extern.java.Log;
import model.Entry;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

@Log
public class DataBase {

  private DataBase() {
  }

  public static List<Entry> getDataFromDB(Long num) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();

    deleteAll("Entry", session);

    for (long i = 1; i <= num; i++) {
      session.save(new Entry(i));
    }
    log.info("Saved number to db;");

    transaction.commit();

    Transaction transaction1 = session.beginTransaction();

    Criteria criteria = session.createCriteria(Entry.class);
    List<Entry> entities = criteria.list();

    log.info("Gave data from db;");

    transaction1.commit();
    session.close();
    HibernateUtil.shutdown();
    return entities;
  }

  private static int deleteAll(String myTable, Session session) {
    String hql = String.format("delete from %s", myTable);
    Query query = session.createQuery(hql);
    log.info("Deleted all data in database;");
    return query.executeUpdate();
  }

}
