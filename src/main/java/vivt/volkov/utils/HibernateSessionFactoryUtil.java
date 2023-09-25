package vivt.volkov.utils;
import vivt.volkov.models.User;
import vivt.volkov.models.JournalRecord;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HibernateSessionFactoryUtil {
    static Logger log = Logger.getLogger("HibernateSessionFactoryUtil");
    private static SessionFactory factory;

    private HibernateSessionFactoryUtil() {}

    public static SessionFactory getFactory() {
        if (factory == null) {
            try {
                Configuration conf = new Configuration().configure();

                conf.addAnnotatedClass(User.class);
                conf.addAnnotatedClass(JournalRecord.class);

                StandardServiceRegistryBuilder builder =
                        new StandardServiceRegistryBuilder().applySettings(conf.getProperties());

                factory = conf.buildSessionFactory(builder.build());
            }
            catch (Exception exc) {
                log.log(Level.SEVERE, "error while configure hibernate", exc);
            }
        }

        return factory;
    }
}
