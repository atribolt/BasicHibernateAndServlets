package vivt.volkov.dao;

import java.util.Optional;
import vivt.volkov.models.User;


public interface UserDAO {
    Optional<User> getByLogin(String login);

    void save(User user);
}
