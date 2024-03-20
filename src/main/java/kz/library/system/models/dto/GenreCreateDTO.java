package kz.library.system.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenreCreateDTO {

    @NotBlank(message = "The genre name is required")
    @Size(min=1, max=50, message = "The length of genre name must be between 1 and 50 characters")
    private String genreName;

    private String description;
}
