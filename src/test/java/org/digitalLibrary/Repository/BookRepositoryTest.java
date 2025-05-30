package org.digitalLibrary.Repository;

import org.digitalLibrary.Exceptions.ResourcenotFoundException;
import org.digitalLibrary.Mappers.output.BookOutputMappers;
import org.digitalLibrary.Services.RedisService;
import org.digitalLibrary.entities.output.BookOutputEntity;
import org.digitalLibrary.enums.BookStatus;
import org.digitalLibrary.model.BookModel;
import org.digitalLibrary.repository.BookRepository;
import org.digitalLibrary.repository.jpa.BookjpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.print.Book;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@ExtendWith(MockitoExtension.class)
public class BookRepositoryTest {
        @Mock
        private BookjpaRepository bookjpaRepository;
        @Mock
        private BookOutputMappers bookOutputMappers;
        @Mock
        private RedisService redisService;
        @InjectMocks
        private BookRepository bookRepository;

        private static BookOutputEntity bookOutputEntity;
        private static BookModel bookModel;
        private static long id;
        private static List<BookModel> bookModellist;
        private static List<BookOutputEntity> bookOutputEntitieslist;

        @BeforeAll
        static void set() {
            id = 1L;
            bookOutputEntity = BookOutputEntity.builder()
                    .id(1L)
                    .name("The Life Story ")
                    .author("kiran")
                    .description("Story of life ")
                    .publishedDate(Instant.now())
                    .createdat(null)
                    .updatedat(Instant.now())
                    .bookStatus(BookStatus.Available)
                    .build();
            bookModel = BookModel.builder()
                    .id(1L)
                    .name("The life Story ")
                    .author("kiran")
                    .description("Story of Life ")
                    .publishedDate(Instant.now())
                    .createdat(null)
                    .updatedat(Instant.now())
                    .bookStatus(BookStatus.Checked_out)
                    .build();

            bookModellist = new ArrayList<>();
            bookModellist.add(bookModel);

            bookOutputEntitieslist = new ArrayList<>();
            bookOutputEntitieslist.add(bookOutputEntity);
        }
        @Test
         @DisplayName("Takes BookModel maps to BookOutputentity save and again map to BookModel and returns ")
        void testsaveBook(){
            Mockito.when(this.bookOutputMappers.mapfromModel(bookModel)).thenReturn(bookOutputEntity);
            Mockito.when(this.bookjpaRepository.save(bookOutputEntity)).thenReturn(bookOutputEntity);
            Mockito.when(this.bookOutputMappers.maptoModel(bookOutputEntity)).thenReturn(bookModel);
            BookModel actualoutput= this.bookRepository.save(bookModel);
            Assertions.assertEquals(actualoutput, bookModel);
        }
        @Test
        @DisplayName("Find book by Id which returns a bookModel")
        void testfindbyId(){
            Mockito.when(this.bookjpaRepository.findById(id)).thenReturn(Optional.of(bookOutputEntity));
            Mockito.when(this.bookOutputMappers.maptoModel(bookOutputEntity)).thenReturn(bookModel);
            BookModel actualoutput = this.bookRepository.findById(id);
            Assertions.assertEquals(actualoutput, bookModel);
        }
        @Test
        @DisplayName("Delete book if Id matches ")
        void testdeletebook(){
            Mockito.when(this.bookjpaRepository.findById(id)).thenReturn(Optional.of(bookOutputEntity));
            Mockito.when(this.bookOutputMappers.maptoModel(bookOutputEntity)).thenReturn(bookModel);
            BookModel actualoutput = this.bookRepository.deleterecords(id);
            Assertions.assertEquals(actualoutput, bookModel);
            Mockito.verify(this.bookjpaRepository).deleteById(id);
        }

        @Test
        @DisplayName("Get all books ")
        void testgetallbooks(){
            Mockito.when(this.bookjpaRepository.findAll()).thenReturn(bookOutputEntitieslist);
            List<BookOutputEntity> actualoutput = this.bookjpaRepository.findAll();
            Assertions.assertEquals(actualoutput, bookOutputEntitieslist);

        }
        @Test
        @DisplayName("update book")
        void testupdate(){
            Mockito.when(this.bookOutputMappers.mapfromModel(bookModel)).thenReturn(bookOutputEntity);
            Mockito.when(this.bookjpaRepository.save(bookOutputEntity)).thenReturn(bookOutputEntity);
            Mockito.when(this.bookOutputMappers.maptoModel(bookOutputEntity)).thenReturn(bookModel);
            BookModel actualoutput = this.bookRepository.update(bookModel);
            Assertions.assertEquals(actualoutput, bookModel);
        }
    @Test
    @DisplayName("Throws ResourcenotFoundException when ID not found in findById")
    void testFindById_ThrowsException_WhenIdNotFound() {
        Mockito.when(bookjpaRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourcenotFoundException.class, () -> {
            bookRepository.findById(id);
        });
    }
    @Test
    @DisplayName("Throws ResourcenotFoundException when ID not found in deleterecords")
    void testDeleteRecords_ThrowsException_WhenIdNotFound() {
        Mockito.when(bookjpaRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourcenotFoundException.class, () -> {
            bookRepository.deleterecords(id);
        });
    }
    @Test
    @DisplayName("update bookstatus will update bookstatus i Cantransition returns true")
    void  testupdatebookstatus(){
        BookStatus currentStatus = BookStatus.Available;
        BookStatus newStatus = BookStatus.Checked_out;

        BookOutputEntity bookOutputEntity1 = new BookOutputEntity();
        bookOutputEntity1.setId(id);
        bookOutputEntity1.setBookStatus(currentStatus);

        Mockito.when(bookjpaRepository.findById(id)).thenReturn(Optional.of(bookOutputEntity));
        Mockito.when(bookjpaRepository.save(bookOutputEntity)).thenReturn(bookOutputEntity);
        Mockito.when(bookOutputMappers.maptoModel(bookOutputEntity)).thenReturn(bookModel);

        BookModel updatedmodel = this.bookRepository.updatestatus(id, newStatus);

        Assertions.assertEquals(newStatus, updatedmodel.getBookStatus());
    }
    @Test
    @DisplayName("Should throw exception when book status transition is invalid")
    void testUpdateBookStatus_InvalidTransition_ThrowsException() {
        Long id = 1L;
        BookStatus currentStatus = BookStatus.Expired;
        BookStatus newStatus = BookStatus.Available;

        BookOutputEntity bookOutputEntity = new BookOutputEntity();
        bookOutputEntity.setId(id);
        bookOutputEntity.setBookStatus(currentStatus);
        Mockito.when(bookjpaRepository.findById(id)).thenReturn(Optional.of(bookOutputEntity));
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bookRepository.updatestatus(id, newStatus);
        });
    }
    @Test
    @DisplayName("Cached test bookbyId will return Bookmodel wrt id")
    void testgetbookbyId(){
        Mockito.when(bookjpaRepository.findById(id)).thenReturn(Optional.of(bookOutputEntity));
        Mockito.when(bookOutputMappers.maptoModel(bookOutputEntity)).thenReturn(bookModel);
        Mockito.when(redisService.get("book" + id)).thenReturn(null);

        BookModel actual = this.bookRepository.getBookbyID(id);
        Assertions.assertEquals(bookModel, actual);
        Mockito.verify(redisService).save("book" + id ,bookModel, 10, TimeUnit.MINUTES );
    }


}



