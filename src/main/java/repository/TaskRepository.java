package repository;

import domain.Task;
import domain.TaskStatus;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    public ArrayList<Task> findByStatus(TaskStatus status);
}
