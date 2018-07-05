package moe.yiheng.dao;

import moe.yiheng.domain.Message;
import moe.yiheng.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class MessageDao {

    public void add(Message message) {
        Session session = HibernateUtil.openSession();
        Transaction tx = session.beginTransaction();

        session.save(message);

        tx.commit();
        session.close();
    }

    public Message findMessageByAskingMessageId(Long askingMessageId) {
        Session session = HibernateUtil.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("from Message where askingMessageId = ?");
        query.setLong(0, askingMessageId);

        List list = query.list();

        tx.commit();
        session.close();
        if (list.isEmpty()) return null;
        return (Message) list.get(0);
    }

    public void update(Message message) {
        Session session = HibernateUtil.openSession();
        Transaction tx = session.beginTransaction();

        session.update(message);

        tx.commit();
        session.close();
    }
}
