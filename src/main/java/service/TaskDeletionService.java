package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import model.dao.*;
import model.entity.TaskBean;

/**
 * 複数タスクの削除をトランザクション内で処理するサービス。
 */
public class TaskDeletionService {

    public List<TaskBean> deleteTasks(List<Integer> taskIds, String userId) throws SQLException {
        if (taskIds == null || taskIds.isEmpty()) throw new IllegalArgumentException("taskIds");
        List<Integer> ids = new ArrayList<>(new LinkedHashSet<>(taskIds));
        if (ids.size() != taskIds.size() || ids.stream().anyMatch(i-> i == null || i <= 0)) throw new IllegalArgumentException("taskIds");
        Collections.sort(ids);
        try (Connection c = ConnectionManager.getConnection()) {
            c.setAutoCommit(false);
            try {
                List<TaskBean> owned = new TaskDAO(c).findOwnedByIdsForUpdate(ids, userId);
                if (owned.size() != ids.size()) {
                    TaskDAO check = new TaskDAO(c);
                    boolean missing = false;
                    for (Integer id : ids) if (!check.existsById(id)) missing = true;
                    if (missing) throw new SQLException("Selected task no longer exists", "02000");
                    throw new SecurityException("E010");
                }
                int comments = new CommentDAO(c).deleteByTaskIds(ids);
                // deleted in same transaction; task rows are locked
                int tasks = new TaskDAO(c).deleteOwnedByIds(ids, userId);
                if (tasks != ids.size()) throw new SQLException("Task deletion count mismatch", "02000");
                c.commit();
                return owned;
            }
            catch (SQLException|RuntimeException e) {
                try {
                    c.rollback();
                }
                catch (SQLException rollback) {
                    e.addSuppressed(rollback);
                }
                throw e;
            }
            finally {
                try {
                    c.setAutoCommit(true);
                }
                catch (SQLException ignored) {
                }
            }
        }
    }
}
