package org.digitalLibrary.Mappers.output;

import org.digitalLibrary.entities.output.BookOutputEntity;
import org.digitalLibrary.model.BookModel;
import org.springframework.stereotype.Component;

@Component
public class BookOutputMappers {

    public BookModel maptoModel(BookOutputEntity bookOutputEntity){
        return BookModel.builder()
                .id(bookOutputEntity.getId())
                .name(bookOutputEntity.getName())
                .author(bookOutputEntity.getAuthor())
                .description(bookOutputEntity.getDescription())
                .createdat(bookOutputEntity.getCreatedat())
                .updatedat(bookOutputEntity.getUpdatedat())
                .bookStatus(bookOutputEntity.getBookStatus())
                .build();
    }

    public BookOutputEntity mapfromModel(BookModel bookModel){
        return BookOutputEntity.builder()
                .id(bookModel.getId())
                .name(bookModel.getName())
                .author(bookModel.getAuthor())
                .description(bookModel.getDescription())
                .publishedDate(bookModel.getPublishedDate())
                .createdat(bookModel.getCreatedat())
                .updatedat(bookModel.getUpdatedat())
                .bookStatus(bookModel.getBookStatus())
                .build();
    }
}
