package kz.library.system.models.dto;

import kz.library.system.domains.entities.Book;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublisherDTO {

    private Long id;

    private String publisherName;

    private int publisherYear;

    private String address;

    private Set<Book> books = new HashSet<>();

    @Override
    public String toString() {
        return "PublisherDTO{" +
                "id=" + id +
                ", publisherName='" + publisherName + '\'' +
                ", publisherYear=" + publisherYear +
                '}';
    }
}
