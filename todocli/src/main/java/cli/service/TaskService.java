package cli.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.time.LocalDateTime;

import cli.domain.Task;
import cli.domain.TaskStatus.*;

public class TaskService {

    ArrayList<Task> todoTasks;
    ArrayList<Task> doingTasks;
    ArrayList<Task> doneTasks;

    public TaskService() {
        todoTasks = new ArrayList<>();
        doingTasks = new ArrayList<>();
        doneTasks = new ArrayList<>();
    }

    public Long getNextId() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public Long create(String title, String description) {
        Task task = new Task(getNextId(), title, description);
        todoTasks.add(task);
        return task.getId();
    }

    public void load(Long id, String title, String description, LocalDateTime startTime, LocalDateTime CompleteTime){
        Task task = new Task(id, title, description, startTime, CompleteTime);
        switch (task.getStatus()) {
            case DONE:
                doneTasks.add(task);
                break;
            case DOING:
                doingTasks.add(task);
                break;
            case DELETED:
                break;
            default:
                todoTasks.add(task);        
        }
    }

    public void save() {
        FileService fileService = new FileService(".task.todo");
        fileService.saveToFile(todoTasks);
    }
}
