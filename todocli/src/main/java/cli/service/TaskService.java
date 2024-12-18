package cli.service;

import cli.domain.Task;
import cli.domain.Task.WrongStatus;
import cli.domain.TaskStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;

public class TaskService {

    ArrayList<Task> todoTasks;
    ArrayList<Task> doingTasks;
    ArrayList<Task> doneTasks;

    public TaskService() {
        todoTasks = new ArrayList<>();
        doingTasks = new ArrayList<>();
        doneTasks = new ArrayList<>();
        load();
    }

    public Long getNextId() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public Long create(String title, String description) {
        Task task = new Task(getNextId(), title, description);
        todoTasks.add(task);
        return task.getId();
    }

    public void load(
        Long id,
        String title,
        String description,
        LocalDateTime startTime,
        LocalDateTime CompleteTime
    ) {
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

    private Boolean changeStatus(
        Task t,
        ArrayList<Task> from,
        ArrayList<Task> to
    ) {
        if (from.contains(t)) {
            try {
                Task task = from.remove(from.indexOf(t));
                if (from.equals(todoTasks)) task.start();
                else if (from.equals(doingTasks)) task.complete();
                return to.add(task);
            } catch (WrongStatus e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    private Boolean changeStatus(
        Long id,
        ArrayList<Task> from,
        ArrayList<Task> to
    ) {
        Task t = new Task(id);
        return changeStatus(t, from, to);
    }

    public Boolean start(Long id) {
        return changeStatus(id, todoTasks, doingTasks);
    }

    public Boolean start(Task task) {
        return changeStatus(task, todoTasks, doingTasks);
    }

    public Boolean complete(Long id) {
        return changeStatus(id, doingTasks, doneTasks);
    }

    public Boolean complete(Task task) {
        return changeStatus(task, doingTasks, doneTasks);
    }

    public ArrayList<Task> list(TaskStatus status) {
        switch (status) {
            case TODO:
                return todoTasks;
            case DONE:
                return doneTasks;
            case DOING:
                return doingTasks;
            default:
                break;
        }
        return null;
    }

    private TaskStatus stringToTaskStatus(String s) {
        switch (s.toUpperCase()) {
            case "TODO":
                return TaskStatus.TODO;
            case "DOING":
                return TaskStatus.DOING;
            case "DONE":
                return TaskStatus.DONE;
            case "DELETED":
                return TaskStatus.DELETED;
        }
        return null;
    }

    public ArrayList<String> list(String status) {
        ArrayList<String> ret = new ArrayList<>();
        TaskStatus taskStatus = stringToTaskStatus(status);
        for (Task t : list(taskStatus)) {
            ret.add(
                String.format("%d - %s - %s", t.getId(), t.getTitle(), status)
            );
        }
        return ret;
    }

    public static Task converter(String line) {
        String[] taskLine = line.split(";");
        return new Task(
            Long.parseLong(taskLine[0]),
            taskLine[1],
            taskLine[2],
            taskLine[3].equals("null")
                ? null
                : LocalDateTime.parse(taskLine[3]),
            taskLine[4].equals("null") ? null : LocalDateTime.parse(taskLine[4])
        );
    }

    public void load() {
        FileService todoFileService = new FileService(".task.todo");
        FileService doneFileService = new FileService(".task.done");
        FileService doingFileServce = new FileService(".task.doing");
        todoTasks.addAll(todoFileService.readFromFile(TaskService::converter));
        doneTasks.addAll(doneFileService.readFromFile(TaskService::converter));
        doingTasks.addAll(doingFileServce.readFromFile(TaskService::converter));
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
