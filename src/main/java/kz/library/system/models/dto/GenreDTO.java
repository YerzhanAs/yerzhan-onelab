package kz.library.system.models.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenreDTO {

    private Long id;

    private String genreName;

    private String description;

    @Override
    public String toString() {
        return "GenreDTO{" +
                "id=" + id +
                ", genreName='" + genreName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
