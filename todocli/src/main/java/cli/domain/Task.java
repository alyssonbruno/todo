package cli.domain;

import java.time.LocalDateTime;

/** class to Task
 *  @author Alysson
 *  @version 2024.11
 */
public class Task implements DataToFile {

    private Long id;

    private String title;
    private String description;
    private TaskStatus status;
    private LocalDateTime startTime;
    private LocalDateTime completeTime;

    public class WrongStatus extends Exception {

        public WrongStatus(String message) {
            super(message);
        }
    }

    /** Create a new Task with TODO status
     *  @author Alysson
     *  @version 2024.11
     */
    public Task(Long id) {
        this.id = id;
    }

    public Task(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        startTime = null;
        completeTime = null;
        status = TaskStatus.TODO;
    }

    public Task(
        Long id,
        String title,
        String description,
        LocalDateTime startTime,
        LocalDateTime completeTime
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.completeTime = completeTime;
        if (startTime != null) {
            if (completeTime != null) {
                this.status = TaskStatus.DONE;
            } else {
                this.status = TaskStatus.DOING;
            }
        } else {
            this.status = TaskStatus.TODO;
        }
    }

    public String toString() {
        return id.toString() + " - " + this.title;
    }

    /** start working in this task
     *
     *  @author Alysson
     *  @version 2024.11
     */
    public void start() throws WrongStatus {
        if (status == TaskStatus.TODO || status == TaskStatus.DELETED) {
            status = TaskStatus.DOING;
            startTime = LocalDateTime.now();
            completeTime = null;
        } else {
            throw new WrongStatus("This task cannot be started!");
        }
    }

    public void complete() throws WrongStatus {
        if (status == TaskStatus.DOING) {
            status = TaskStatus.DONE;
            completeTime = LocalDateTime.now();
        } else {
            throw new WrongStatus("This task cannot be completed!");
        }
    }

    public void cancel() throws WrongStatus {
        if (status == TaskStatus.DOING || status == TaskStatus.TODO) {
            status = TaskStatus.DELETED;
            completeTime = LocalDateTime.now();
        } else {
            throw new WrongStatus("This taks cannot be canceled!");
        }
    }

    public void reset() {
        status = TaskStatus.TODO;
        startTime = null;
        completeTime = null;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public void setDescripion(String newDescrition) {
        description = newDescrition;
    }

    public String getTitle() {
        return title;
    }

    public String geDescricao() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getCompleteTime() {
        return completeTime;
    }

    @Override
    public String toLine() {
        return String.format(
            "%d;%s;%s;%s;%s\n",
            id,
            title,
            description,
            startTime.equals(null) ? "" : startTime.toString(),
            completeTime.equals(null) ? "" : completeTime.toString()
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Task) {
            return this.id == ((Task) other).id;
        }
        return false;
    }
}
