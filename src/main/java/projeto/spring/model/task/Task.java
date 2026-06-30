package projeto.spring.model.task;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projeto.spring.dto.TaskRequestDto;
import projeto.spring.dto.TaskResponseDto;
import projeto.spring.model.user.User;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Task(TaskRequestDto data){
        this.title = data.title();
        this.description = data.description();
        this.status = data.status();
        this.createdAt = LocalDateTime.now();
    }
}
