package projeto.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import projeto.spring.model.Status;

import java.time.LocalDateTime;

public class TaskDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Status status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}