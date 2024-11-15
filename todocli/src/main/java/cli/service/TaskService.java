package cli.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.time.LocalDateTime;

import cli.domain.Task;
import cli.domain.Task.WrongStatus;
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
        //the status is calculated from startTime and CompleteTime
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

    private Boolean changeStatus(Task t, ArrayList<Task> from, ArrayList<Task> to){
        if(from.contains(t)){
            try {
                Task task = from.remove(from.indexOf(t));
                if(from.equals(todoTasks))
                    task.start();
                else if (from.equals(doingTasks))
                    task.complete();
                return to.add(task);
            }
            catch(WrongStatus e){
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    private Boolean changeStatus(Long id, ArrayList<Task> from, ArrayList<Task> to){
        Task t = new Task(id);
        return changeStatus(t, from, to);
    }


    public Boolean start(Long id){
        return changeStatus(id, todoTasks, doingTasks);
    }

    public Boolean start(Task task){
        return changeStatus(task, todoTasks, doingTasks);
    }

    public Boolean complete(Long id){
        return changeStatus(id, doingTasks, doneTasks);
    }

    public Boolean complete(Task task){
        return changeStatus(task, doingTasks, doneTasks);
    }

    public void save() {
        FileService todoFileService = new FileService(".task.todo");
        FileService doneFileService = new FileService(".task.done");
        FileService doingFileServce = new FileService(".task.doing");
        todoFileService.saveToFile(todoTasks);
        doneFileService.saveToFile(doneTasks);
        doingFileServce.saveToFile(doingTasks);
    }
}
