package kz.library.system.models.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchBookRequest {

    private String title;

    private String isbn;

    private String language;
}
