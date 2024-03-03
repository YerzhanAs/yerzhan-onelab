package kz.library.system.models.dto;

import kz.library.system.domains.entities.Book;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublisherDTO {

    private Long id;

    private String publisherName;

    private int publisherYear;

    private List<Book> books = new ArrayList<>();

    @Override
    public String toString() {
        return "PublisherDTO{" +
                "id=" + id +
                ", publisherName='" + publisherName + '\'' +
                ", publisherYear=" + publisherYear +
                ", books=" + books +
                '}';
    }
}
