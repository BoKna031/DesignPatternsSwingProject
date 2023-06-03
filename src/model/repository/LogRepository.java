package model.repository;

import java.util.ArrayList;
import java.util.List;

public class LogRepository implements ILogRepository{
    ArrayList<String> logs = new ArrayList<>();


    @Override
    public void addLog(String logEntry) {
        logs.add(logEntry);
    }

    @Override
    public List<String> getAllLogs() {
        return logs;
    }

    @Override
    public void clearLogs() {
        logs.clear();
    }
}
