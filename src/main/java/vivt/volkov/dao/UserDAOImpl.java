package vivt.volkov.dao;

import vivt.volkov.models.User;
import vivt.volkov.utils.HibernateSessionFactoryUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


public class UserDAOImpl implements UserDAO {
    final static Logger log = Logger.getLogger("UserDAOImpl");

    @Override
    public Optional<User> getByLogin(String login) {
        try (Session session = HibernateSessionFactoryUtil.getFactory().openSession()) {
             List<User> users = session
                     .createQuery("from User where login = :login", User.class)
                     .setParameter("login", login)
                     .list();
             if (users.isEmpty()) {
                 log.warning("user with login '" + login + "' not found");
                 return Optional.empty();
             }

             return Optional.of(users.get(0));
        }
    }

    @Override
    public void save(User user) {
        try (Session session = HibernateSessionFactoryUtil.getFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(user);
            tx.commit();

            log.fine("user saved into database");
        }
    }
}
