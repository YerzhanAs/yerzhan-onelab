package kz.library.system.models.dto;

import lombok.*;

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

}
