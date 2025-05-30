    package org.digitalLibrary.model;

    import lombok.*;
    import org.digitalLibrary.enums.BookStatus;

    import java.io.Serializable;
    import java.time.Instant;

    @Data
    @Getter
    @Setter
    @Builder
    @With
    public class BookModel implements Serializable {

        private static final long serialVersionUID= 1L;

        private long id;
        private String name;
        private String author;
        private String description;
        private Instant publishedDate;
        private Instant createdat;
        private Instant updatedat;
        private BookStatus bookStatus;

    }
