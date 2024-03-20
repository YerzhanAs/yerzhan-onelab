package kz.library.system.domains.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name= "t_publishers")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The publisher name name is required")
    @Column(name = "publisher_name")
    private String publisherName;

    @Column(name = "publisher_year")
    private int publisherYear;

    @Column(name = "address")
    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publisher publisher = (Publisher) o;
        return publisherYear == publisher.publisherYear && Objects.equals(id, publisher.id) && Objects.equals(publisherName, publisher.publisherName) && Objects.equals(address, publisher.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, publisherName, publisherYear, address);
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "id=" + id +
                ", publisherName='" + publisherName + '\'' +
                ", publisherYear='" + publisherYear + '\'' +
                '}';
    }
}
