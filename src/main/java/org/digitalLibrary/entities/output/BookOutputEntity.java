package org.digitalLibrary.entities.output;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.digitalLibrary.enums.BookStatus;

import java.time.Instant;

@Entity
@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Book")
public class BookOutputEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "book_seq")
    @SequenceGenerator(name = "book_seq", sequenceName = "book_seq", allocationSize = 1)
    @Column(name = "BOOK_ID", nullable = false)
    private long id;

    @Column(name = "BOOK_NAME", nullable = false)
    private String name;

    @Column(name = "BOOK_AUTHOR", nullable = false)
    private String author;

    @Column(name = "BOOK_DESCRIPTION")
    private String description;

    @Column(name = "PUBISHED_DATE")
    private Instant publishedDate;

    @Column(name = "CRESTED_AT")
    private Instant createdat;

    @Column(name = "UPDATED_AT")
    private Instant updatedat;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;
}
