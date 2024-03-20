package kz.library.system.models.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDTO {

    private Long id;
    private String name;
    private Set<BookDTO> books;
}
