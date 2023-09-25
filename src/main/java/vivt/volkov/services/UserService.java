package vivt.volkov.services;

import vivt.volkov.dao.*;
import vivt.volkov.models.User;
import vivt.volkov.models.ActionTypes;
import vivt.volkov.models.JournalRecord;
import vivt.volkov.models.StatisticRecord;

import javax.enterprise.inject.Default;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;


public class UserService {
    final static Logger log = Logger.getLogger("UserService");
    private static UserService _instance;
    private UserDAO userDAO;
    private StatisticDAO statisticDAO;
    private JournalDAO journalDAO;

    private UserService(UserDAO userDAO, StatisticDAO statisticDAO, JournalDAO journalDAO) {
        this.userDAO = userDAO;
        this.statisticDAO = statisticDAO;
        this.journalDAO = journalDAO;
    }

    public static UserService instance() {
        if (_instance == null) {
            _instance = new UserService(
                    new UserDAOImpl(),
                    new StatisticDAOImpl(),
                    new JournalDAOImpl()
            );
        }

        return _instance;
    }

    public Optional<User> getUserByLogin(String login) {
        log.fine("user requested: " + login);
        return userDAO.getByLogin(login);
    }

    public void loginUser(User user, String host) {
        log.fine("user is login: " + user.getLogin());

        StatisticRecord stat = statisticDAO.getStatisticForUser(user);
        JournalRecord rec = new JournalRecord(user, ActionTypes.LOGIN, host);

        stat.incrementLogin();

        journalDAO.addRecord(rec);
        statisticDAO.updateStatistic(stat);
    }

    public Optional<User> registrationUser(String login, String name, String surname, String patronymic, String host) {
        String password = UUID.randomUUID().toString().replace("-", "");

        User user = new User(login, password, name, surname, patronymic);
        userDAO.save(user);

        Optional<User> u = userDAO.getByLogin(login);
        if (u.isPresent()) {
            log.fine("user registered success: " + login);
            journalDAO.addRecord(new JournalRecord(user, ActionTypes.REGISTRATION, host));
        }
        else
            log.warning("error while find user after registration: " + login);

        return u;
    }
}
