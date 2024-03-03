package kz.library.system.domains.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    private String publisherName;

    private int publisherYear;

    @OneToMany(mappedBy = "publisher")
    private List<Book> books = new ArrayList<>();

    @Override
    public String toString() {
        return "Publisher{" +
                "id=" + id +
                ", publisherName='" + publisherName + '\'' +
                ", publisherYear='" + publisherYear + '\'' +
                ", books=" + books +
                '}';
    }
}
