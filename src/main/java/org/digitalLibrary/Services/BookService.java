package org.digitalLibrary.Services;

import org.digitalLibrary.enums.BookStatus;
import org.digitalLibrary.model.BookModel;
import org.digitalLibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookModel addbook(BookModel bookModel){
       return this.bookRepository.save(bookModel);
    }
    public BookModel findBoookById(long id){
        return this.bookRepository.findById(id);
    }

    public BookModel deletebook(long id){
        return this.bookRepository.deleterecords(id);
    }

    public List<BookModel> getallbooks(){
        return this.bookRepository.getAll();
    }
    public BookModel updatebook(BookModel bookModel){
        return this.bookRepository.update(bookModel);
    }
    public BookModel updatebookstatus(long bookid, BookStatus bookStatus){
        return this.bookRepository.updatestatus(bookid, bookStatus);
    }
    public BookModel getbookbyid(long id){
        return this.bookRepository.getBookbyID(id);
    }

}
