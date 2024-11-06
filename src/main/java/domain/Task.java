package domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

/** class to Task
 *  @author Alysson
 *  @version 2024.11
 */
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String about;
    private TaskStatus status;
    private LocalDateTime startTime;
    private LocalDateTime completeTime;

    public class WrongStatus extends Exception {

        public WrongStatus(String message) {
            super(message);
        }
    }

    protected Task() {}

    /** Create a new Task with TODO status
     *  @author Alysson
     *  @version 2024.11
     */
    public Task(Long id, String about) {
        this.id = id;
        this.about = about;
        status = TaskStatus.TODO;
    }

    public String toString() {
        return id.toString() + " - " + this.about;
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

    public void setAbout(String newAbout) {
        about = newAbout;
    }

    public String getAbout() {
        return about;
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
}
