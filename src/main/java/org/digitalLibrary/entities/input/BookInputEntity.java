package org.digitalLibrary.entities.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.digitalLibrary.enums.BookStatus;

import java.time.Instant;

@Data
@Builder
@Getter
@Setter
@With
public class BookInputEntity {

    private long id;
    @NotBlank(message = "name is required cannot be null")
    private String name;

    @NotBlank(message = "author name is required cannot be null")
    private String author;

    private String description;
    @NotNull(message = "published date is reuqired")
    private Instant publishedDate;

    private BookStatus bookStatus;



}
