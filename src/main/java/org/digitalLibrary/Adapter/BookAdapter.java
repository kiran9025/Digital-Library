package org.digitalLibrary.Adapter;

import org.digitalLibrary.Mappers.Input.BookInputmapper;
import org.digitalLibrary.Services.BookService;
import org.digitalLibrary.entities.input.BookInputEntity;
import org.digitalLibrary.enums.BookStatus;
import org.digitalLibrary.model.BookModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.print.Book;
import java.util.List;

@Component
public class BookAdapter {

    @Autowired
    private final BookInputmapper bookInputmapper;
    private final BookService bookService;

    @Autowired
    public BookAdapter(BookInputmapper bookInputmapper, BookService bookService) {
        this.bookInputmapper = bookInputmapper;
        this.bookService = bookService;
    }

    public BookModel save(BookInputEntity bookInputEntity){
        return this.bookService.addbook(this.bookInputmapper.maptoModel(bookInputEntity));
    }

    public BookModel findById(long id){
        return this.bookService.findBoookById(id);
    }



    public BookModel deletebook(long id) {
        return this.bookService.deletebook(id);
    }

    public List<BookModel> getallbooks() {
        return this.bookService.getallbooks();
    }

    public BookModel updatebook(BookInputEntity bookInputEntity) {
        return this.bookService.updatebook(this.bookInputmapper.maptoModel(bookInputEntity));
    }

    public BookModel updatestatus(long bookid, BookStatus bookStatus){
        return this.bookService.updatebookstatus(bookid, bookStatus);
    }

    public BookModel getbookbyId(long id){
        return this.bookService.getbookbyid(id);
    }

}
