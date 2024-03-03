package kz.library.system.models.dto;

import kz.library.system.domains.entities.Author;
import kz.library.system.domains.entities.Publisher;
import lombok.*;

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

    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", language='" + language + '\'' +
                ", author=" + author +
                ", publisher=" + publisher +
                '}';
    }
}
