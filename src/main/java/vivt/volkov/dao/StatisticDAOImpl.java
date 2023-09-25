package vivt.volkov.dao;

import vivt.volkov.models.User;
import vivt.volkov.models.StatisticRecord;

import org.hibernate.Session;
import org.hibernate.Transaction;
import vivt.volkov.utils.HibernateSessionFactoryUtil;

import java.util.logging.Logger;


public class StatisticDAOImpl implements StatisticDAO {
    final static Logger log = Logger.getLogger("StatisticDAOImpl");

    @Override
    public StatisticRecord getStatisticForUser(User user) {
        try (Session session = HibernateSessionFactoryUtil.getFactory().openSession()) {
            StatisticRecord rec = session.get(StatisticRecord.class, user.getId());
            return (rec == null) ? new StatisticRecord(user) : rec;
        }
    }

    @Override
    public void updateStatistic(StatisticRecord rec) {
        try (Session session = HibernateSessionFactoryUtil.getFactory().openSession()) {
            Transaction tr = session.beginTransaction();

            if (rec.getId() == null)
                session.save(rec);
            else
                session.update(rec);
            tr.commit();

            log.fine("statistic updated: " + rec);
        }
    }
}
