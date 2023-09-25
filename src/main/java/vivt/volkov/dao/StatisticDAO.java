package vivt.volkov.dao;

import vivt.volkov.models.StatisticRecord;
import vivt.volkov.models.User;

public interface StatisticDAO {
    public StatisticRecord getStatisticForUser(User user);
    public void updateStatistic(StatisticRecord rec);
}
