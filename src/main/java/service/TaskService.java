package service;

import domain.Task;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;
import repository.TaskRepository;

@Service
public class TaskService {

    @Inject
    TaskRepository taskRepository;

    public Long add(String title, String descrition) {
        Long id = taskRepository.save(new Task(title, descrition)).getId();
        return id;
    }

    public Long add(String title) {
        Long id = taskRepository.save(new Task(title, "")).getId();
        return id;
    }
}
