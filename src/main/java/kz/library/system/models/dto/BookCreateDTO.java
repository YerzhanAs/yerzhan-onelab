package kz.library.system.models.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookCreateDTO {

    @NotEmpty(message = "The title is required")
    @Size(min=1, max=50,
            message = "The length of genre name must be between 1 and 50 characters")
    private String title;

    @Pattern(regexp = "^(978|979)-\\d{1,5}-\\d{1,7}-\\d{1,7}-\\d$",
            message = "The ISBN must match the ISBN-13 format")
    private String isbn;

    @NotEmpty(message = "The language is required")
    private String language;

}
