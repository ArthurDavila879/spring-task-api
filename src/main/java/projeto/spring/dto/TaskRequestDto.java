package projeto.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projeto.spring.model.Status;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TaskRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Status status;

    @NotNull
    private Long idUser;

    public Long getIdUser() {
        return idUser;
    }
}