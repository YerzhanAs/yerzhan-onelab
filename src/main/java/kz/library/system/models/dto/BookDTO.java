package kz.library.system.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kz.library.system.domains.entities.Publisher;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {

    private Long id;

    private String title;

    private String isbn;

    private String language;

    private Set<GenreDTO> genres;

    private List<PublisherDTO> publishers;
}
