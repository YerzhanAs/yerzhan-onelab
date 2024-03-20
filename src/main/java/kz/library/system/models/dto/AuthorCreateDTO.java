package kz.library.system.models.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorCreateDTO {

    @NotBlank
    private String name;
}
