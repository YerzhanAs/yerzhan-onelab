package kz.library.system.models.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.Set;

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

    @Pattern(regexp = "^\\d{3}-\\d{1}-\\d{3}-\\d{5}-\\d{1}$",
            message = "The ISBN must match the ISBN-13 format")
    private String isbn;

    @NotEmpty(message = "The language is required")
    private String language;

}
