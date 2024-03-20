package kz.library.system.models.mapper;

import kz.library.system.domains.entities.Publisher;
import kz.library.system.models.dto.PublisherDTO;
import kz.library.system.models.dto.PublisherCreateDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublisherMapper {

    PublisherDTO toPublisherDTO(Publisher publisher);

    Publisher toPublisher(PublisherDTO publisherDTO);

    Publisher toPublisher(PublisherCreateDTO publisherCreateDTO);
}
