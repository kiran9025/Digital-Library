package org.digitalLibrary.Service;

import org.digitalLibrary.Services.BookService;
import org.digitalLibrary.enums.BookStatus;
import org.digitalLibrary.model.BookModel;
import org.digitalLibrary.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
        @Mock
        private BookRepository bookRepository;

        @InjectMocks
        private BookService bookService;
        private static BookModel bookModel;
        private static List<BookModel> bookModellist;
        private static long  id;

        @BeforeAll
        static void set(){
            id = 1L;
            bookModel =  BookModel.builder()
                    .id(1L)
                    .name("The life Story ")
                    .author("kiran")
                    .description("Story of Life ")
                    .publishedDate(Instant.now())
                    .createdat(null)
                    .updatedat(Instant.now())
                    .bookStatus(BookStatus.Available)
                    .build();

            bookModellist = new ArrayList<>();
            bookModellist.add(bookModel);
        }
        @Test
        @DisplayName("Save Book with input BookModel and returns BookModel")
        void testaddBook(){
            Mockito.when(this.bookRepository.save(bookModel)).thenReturn(bookModel);
            BookModel actualoutput = this.bookService.addbook(bookModel);
            Assertions.assertEquals(actualoutput, bookModel);
        }

    @Test
    @DisplayName("Find book by ID and return BookModel")
    void testFindBookById() {
        Mockito.when(this.bookRepository.findById(id)).thenReturn(bookModel);
        BookModel actual = this.bookService.findBoookById(id);
        Assertions.assertEquals(bookModel, actual);
    }

    @Test
    @DisplayName("Delete book by ID and return deleted BookModel")
    void testDeleteBook() {
        Mockito.when(this.bookRepository.deleterecords(id)).thenReturn(bookModel);
        BookModel actual = this.bookService.deletebook(id);
        Assertions.assertEquals(bookModel, actual);
    }

    @Test
    @DisplayName("Get all books and return list of BookModel")
    void testGetAllBooks() {
        List<BookModel> expectedList = List.of(bookModel);
        Mockito.when(this.bookRepository.getAll()).thenReturn(expectedList);
        List<BookModel> actualList = this.bookService.getallbooks();
        Assertions.assertEquals(expectedList, actualList);
    }
    @Test
    @DisplayName("Update book without ID and return updated BookModel")
    void testUpdateBook() {
        Mockito.when(this.bookRepository.update(bookModel)).thenReturn(bookModel);
        BookModel actual = this.bookService.updatebook(bookModel);
        Assertions.assertEquals(bookModel, actual);
    }
    @Test
    @DisplayName("upadte status should return bookmodel")
    void testupdatebookstatus(){
        BookStatus bookStatus = bookModel.getBookStatus();
        Mockito.when(this.bookRepository.updatestatus(id, bookStatus)).thenReturn(bookModel);
        BookModel bookModel1 = this.bookService.updatebookstatus(id,bookStatus);
        Assertions.assertEquals(bookModel1, bookModel);

    }
    @Test
    @DisplayName("getbook byid return bookmodel ")
    void testgetbookbyId(){
            Mockito.when(this.bookRepository.getBookbyID(id)).thenReturn(bookModel);
            BookModel actual = this.bookService.getbookbyid(id);
            Assertions.assertEquals(bookModel, actual);
    }

}
