package org.digitalLibrary.repository;

import org.digitalLibrary.Exceptions.BookStatusNotTransitionException;
import org.digitalLibrary.Exceptions.ResourcenotFoundException;
import org.digitalLibrary.Mappers.output.BookOutputMappers;
import org.digitalLibrary.Services.RedisService;
import org.digitalLibrary.entities.output.BookOutputEntity;
import org.digitalLibrary.enums.BookStatus;
import org.digitalLibrary.model.BookModel;
import org.digitalLibrary.repository.jpa.BookjpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class BookRepository {

    @Autowired
    private final BookjpaRepository bookjpaRepository;
    private final BookOutputMappers bookOutputMappers;
    private final RedisService redisService;
    @Autowired
    public BookRepository(BookjpaRepository bookjpaRepository, BookOutputMappers bookOutputMappers, RedisService redisService) {
        this.bookjpaRepository = bookjpaRepository;
        this.bookOutputMappers = bookOutputMappers;
        this.redisService = redisService;
    }
    public BookModel save(BookModel bookModel){
        BookOutputEntity bookOutputEntity1 = this.bookOutputMappers.mapfromModel(bookModel);
        BookOutputEntity savedoutputetnity = this.bookjpaRepository.save(bookOutputEntity1);
        return this.bookOutputMappers.maptoModel(savedoutputetnity);
    }
    public BookModel findById(long id){
        Optional<BookOutputEntity> optional = this.bookjpaRepository.findById(id);
        return optional
                .map(this.bookOutputMappers::maptoModel)
                .orElseThrow(()-> new ResourcenotFoundException(BookModel.class, "id", String.valueOf(id)));

    }
    public BookModel deleterecords(long id){
        Optional<BookOutputEntity> optional = this.bookjpaRepository.findById(id);
        if(optional.isPresent()){
            BookOutputEntity deltedentiy = optional.get();
            this.bookjpaRepository.deleteById(id);
            return this.bookOutputMappers.maptoModel(deltedentiy);
        }else{
            throw new ResourcenotFoundException(BookModel.class, "id", String.valueOf(id));
        }

    }

    public List<BookModel> getAll(){
        List<BookOutputEntity> bookOutputEntity = this.bookjpaRepository.findAll();
        return bookOutputEntity.stream()
                .map(bookOutputMappers::maptoModel)
                .collect(Collectors.toList());
    }
    public BookModel update(BookModel bookModel){
        BookOutputEntity bookOutputEntity = this.bookOutputMappers.mapfromModel(bookModel);
        BookOutputEntity bookOutput = this.bookjpaRepository.save(bookOutputEntity);
        return this.bookOutputMappers.maptoModel(bookOutput);

    }
    public BookModel updatestatus(Long bookid, BookStatus newstatus){
        Optional<BookOutputEntity> optional = this.bookjpaRepository.findById(bookid);
        if(optional.isPresent()){
            BookOutputEntity bookOutputEntity = optional.get();
            BookStatus currentstatus = bookOutputEntity.getBookStatus();
            if(!currentstatus.canTransitionTo(newstatus)){
                throw new BookStatusNotTransitionException("Cannot Change The status");
            }
            bookOutputEntity.setBookStatus(newstatus);
            this.bookjpaRepository.save(bookOutputEntity);

        }
        return optional
                .map(bookOutputMappers::maptoModel)
                .orElseThrow(() -> new ResourcenotFoundException(BookModel.class, "bookid", String.valueOf(bookid)));
    }

    public BookModel getBookbyID(long id){
        String key = "book" + id;
        Object Cachedbook = redisService.get(key);

        if(Cachedbook != null){
            System.out.println("Fetched from cache");
            return (BookModel) Cachedbook;
        }
        System.out.println("Fetched from Database");
        BookOutputEntity book = this.bookjpaRepository.findById(id).orElse(null);
        if(book == null){
            return null;
        }
        BookModel bookModel1 = this.bookOutputMappers.maptoModel(book);
        redisService.save(key,bookModel1, 10, TimeUnit.MINUTES);
        return bookModel1;
        }

}
