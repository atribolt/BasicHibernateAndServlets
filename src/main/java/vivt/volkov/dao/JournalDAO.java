package vivt.volkov.dao;

import vivt.volkov.models.JournalRecord;

import java.util.List;


public interface JournalDAO {
    public List<JournalRecord> getLastRecords(int count);
    public void addRecord(JournalRecord rec);
}
