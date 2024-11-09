package service;

import java.util.ArrayList;
import java.util.Calendar;

import domain.Task;


public class TaskService {

    ArrayList<Task> todoTasks;

    public TaskService(){
        todoTasks = new ArrayList<>();
    }

    public Long getNextId() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public Long create(String title, String description) {
        Task task = new Task(getNextId(), title, description);
        todoTasks.add(task);
        return task.getId();
    }

    public void save() {
        FileService fileService = new FileService(".task.todo");
        fileService.saveToFile(todoTasks);
    }

}
