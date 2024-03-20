package kz.library.system.domains.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name= "t_genres",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "genre_name"),
        })
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "genre_name")
    @NotEmpty(message = "The genre name is required")
    @Size(min=1, max=50, message = "The length of genre name must be between 1 and 50 characters")
    private String genreName;

    @Column(name = "description")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(id, genre.id) && Objects.equals(genreName, genre.genreName) && Objects.equals(description, genre.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, genreName, description);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", genreName='" + genreName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
