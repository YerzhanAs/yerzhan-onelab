package kz.library.system.model;

import kz.library.system.domain.entities.Author;
import kz.library.system.domain.entities.Publisher;
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
