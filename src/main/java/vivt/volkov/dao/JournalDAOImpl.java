package vivt.volkov.dao;

import vivt.volkov.models.ActionType;
import vivt.volkov.models.JournalRecord;
import vivt.volkov.utils.HibernateSessionFactoryUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.logging.Logger;

public class JournalDAOImpl implements JournalDAO {
    final static Logger log = Logger.getLogger("JournalDAOImpl");
    @Override
    public List<JournalRecord> getLastRecords(int count) {
        try (Session session = HibernateSessionFactoryUtil.getFactory().openSession()) {
            return session.createQuery("", JournalRecord.class).stream().limit(count).toList();
        }
    }

    @Override
    public void addRecord(JournalRecord rec) {
        try (Session session = HibernateSessionFactoryUtil.getFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            session.saveOrUpdate(rec.getAction());
            session.save(rec);

            tx.commit();

            log.fine("new record saved: " + rec);
        }
    }
}
