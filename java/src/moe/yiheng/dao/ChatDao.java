package moe.yiheng.dao;

import moe.yiheng.domain.Chat;
import moe.yiheng.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ChatDao {
    public void add(Chat chat) {
        Session session = HibernateUtil.openSession();
        Transaction tx = session.beginTransaction();

        session.save(chat);
        tx.commit();
        session.close();
    }

    public Chat findById(Long id) {
        Session session = HibernateUtil.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("from Chat where id = ?");
        query.setLong(0, id);

        List list = query.list();
        if (list.isEmpty()){
            return null;
        }
        tx.commit();
        session.close();
        return (Chat) list.get(0);
    }

    public void update(Chat chat) {
        Session session = HibernateUtil.openSession();
        Transaction tx = session.beginTransaction();

        session.update(chat);

        tx.commit();
        session.close();
    }
}
