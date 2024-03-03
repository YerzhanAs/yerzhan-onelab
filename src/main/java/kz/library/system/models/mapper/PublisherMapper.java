package kz.library.system.models.mapper;

import kz.library.system.domains.entities.Publisher;
import kz.library.system.models.dto.PublisherDTO;

public class PublisherMapper {

    public static PublisherDTO entityToDto(Publisher publisher) {
        return PublisherDTO.builder()
                .id(publisher.getId())
                .publisherName(publisher.getPublisherName())
                .publisherYear(publisher.getPublisherYear())
                .build();
    }

    public static Publisher dtoToEntity(PublisherDTO publisherDTO) {
        return Publisher.builder()
                .id(publisherDTO.getId())
                .publisherName(publisherDTO.getPublisherName())
                .publisherYear(publisherDTO.getPublisherYear())
                .build();
    }
}
