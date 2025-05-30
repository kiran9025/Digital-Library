package org.digitalLibrary.Mappers.Input;

import org.digitalLibrary.entities.input.BookInputEntity;
import org.digitalLibrary.model.BookModel;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class BookInputmapper {

    public BookModel maptoModel(BookInputEntity bookInputEntity){
        return BookModel.builder()
                .id(bookInputEntity.getId())
                .name(bookInputEntity.getName())
                .author(bookInputEntity.getAuthor())
                .description(bookInputEntity.getDescription())
                .publishedDate(Instant.now())
                .bookStatus(bookInputEntity.getBookStatus())
                .build();
    }

}
