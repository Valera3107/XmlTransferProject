package service;

import model.Num;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class DataBase {

  private DataBase() {
  }

  public static List<Num> getDataFromDB(Long num) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = session.beginTransaction();

    deleteAll("model.Num", session);

    for (long i = 1; i <= num; i++) {
      session.save(new Num(i));
    }

    transaction.commit();

    Transaction transaction1 = session.beginTransaction();

    Criteria criteria = session.createCriteria(Num.class);
    List<Num> entities = criteria.list();

    transaction1.commit();
    session.close();
    return entities;
  }

  private static int deleteAll(String myTable, Session session) {
    String hql = String.format("delete from %s", myTable);
    Query query = session.createQuery(hql);
    return query.executeUpdate();
  }

}
