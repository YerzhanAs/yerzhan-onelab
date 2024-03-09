package kz.library.system.models.dto;

import kz.library.system.domains.entities.Author;
import kz.library.system.domains.entities.Genre;
import kz.library.system.domains.entities.Publisher;
import lombok.*;

import java.util.HashSet;
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

    private Author author;

    private Publisher publisher;

    private Set<Genre> genres = new HashSet<>();

    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", language='" + language + '\'' +
                ", genres=" + genres +
                '}';
    }
}
