package model.repository;

import java.util.List;

public interface ILogRepository {
    void addLog(String logEntry);
    List<String> getAllLogs();

    void clearLogs();
}
