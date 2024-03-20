package kz.library.system.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublisherCreateDTO {

    @NotBlank(message = "The publisher name is required")
    private String publisherName;

    private int publisherYear;

    @NotBlank
    private String address;
}
