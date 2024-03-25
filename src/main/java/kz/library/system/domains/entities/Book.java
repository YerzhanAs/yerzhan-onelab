package kz.library.system.domains.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name= "t_books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The title is required")
    @Size(min=1, max=50,
            message = "The length of genre name must be between 1 and 50 characters")
    private String title;

    @Pattern(regexp = "^(978|979)-\\d{1,5}-\\d{1,7}-\\d{1,7}-\\d$",
            message = "The ISBN must match the ISBN-13 format")
    private String isbn;

    @NotEmpty(message = "The language is required")
    private String language;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToMany(
            cascade = { CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "books_genres",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new HashSet<>();


    @ManyToMany(
            cascade = { CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "books_publishers",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "publisher_id"))
    private List<Publisher> publishers = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(isbn, book.isbn) && Objects.equals(language, book.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, isbn, language);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", language='" + language + '\'' +
                ", author=" + author +
                '}';
    }
}
