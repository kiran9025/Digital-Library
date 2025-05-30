package org.digitalLibrary.Controller;

import org.digitalLibrary.Adapter.BookAdapter;
import org.digitalLibrary.Exceptions.ResourcenotFoundException;
import org.digitalLibrary.entities.input.BookInputEntity;
import org.digitalLibrary.enums.BookStatus;
import org.digitalLibrary.model.BookModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.time.Instant;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {
    @Mock
    private BookAdapter bookAdapter;
    @InjectMocks
    private BookController bookController;
    static BookInputEntity bookInputEntity;
    static BookModel bookModel;
    static long id ;
    static List<BookModel> bookModellist;


    @BeforeAll
    static void set(){
        id = 1L;
        bookInputEntity = BookInputEntity.builder()
                .id(1L)
                .name("The Life Story ")
                .author("kiran")
                .description("Story of life ")
                .publishedDate(Instant.now())
                .bookStatus(BookStatus.Available)
                .build();

        bookModel =  BookModel.builder()
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

    }

   @Test
   @DisplayName("test addbook")
    void testaddbook(){
        Mockito.when(bookAdapter.save(bookInputEntity)).thenReturn(bookModel);
        ResponseEntity<?> response = this.bookController.addbook(bookInputEntity);

        Assertions.assertEquals(bookModel, response.getBody());
        Assertions.assertEquals(HttpStatus.CREATED,response.getStatusCode());
    }

    @Test
    @DisplayName("findbyID")
    void testfindById(){
        Mockito.when(bookAdapter.findById(id)).thenReturn(bookModel);
        ResponseEntity<?> response = this.bookController.finddByID(id);
        Assertions.assertEquals(bookModel,response.getBody());
    }

    @Test
    @DisplayName("update book by id")
    void testupdatebookbyId(){
        Mockito.when(bookAdapter.updatebook(bookInputEntity)).thenReturn(bookModel);
        ResponseEntity<?> response = this.bookController.updatebook(bookInputEntity);
        Assertions.assertEquals(bookModel,response.getBody());
    }
    @Test
    @DisplayName("Delete  book by id")
    void testdeletebook(){
        Mockito.when(bookAdapter.deletebook(id)).thenReturn(bookModel);

        ResponseEntity<?> response = this.bookController.deleteBook(id);
        Assertions.assertEquals(bookModel,response.getBody());
    }

    @Test
    @DisplayName("Delete book if Id not found throws Resource not found exception ")
    void testDeleteBookNotFound(){
        Mockito.when(this.bookAdapter.deletebook(id)).thenThrow(new ResourcenotFoundException(Mockito.any()));
        ResponseEntity<?> response = this.bookController.deleteBook(id);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("get all books")
    void testgetallbooks(){
        Mockito.when(bookAdapter.getallbooks()).thenReturn(bookModellist);
        List<BookModel> list = this.bookController.getallbooks();
        Assertions.assertEquals(list,bookModellist);
    }

    @Test
    @DisplayName("Update book Id not found lead to ResourcenotFoundException")
    void testUpdatebookdnotFound(){
        Mockito.when(this.bookAdapter.updatebook(bookInputEntity)).thenThrow(new ResourcenotFoundException(Mockito.any()));
        ResponseEntity<?> response = this.bookController.updatebook(bookInputEntity);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    @DisplayName("update bookstatus will update bookstatus if Cantransition returns true")
    void  testupdatebookstatus(){
        BookStatus bookStatus = bookInputEntity.getBookStatus();
        Mockito.when(this.bookAdapter.updatestatus(id, bookStatus)).thenReturn(bookModel);
        ResponseEntity<?> response = this.bookController.updatebookstatus(id, bookStatus);
        Assertions.assertEquals(bookModel, response.getBody());
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("update bookstatus will return a BAD_request if id not found")
    void  testupdatebookstatus_idstatusnotTransists(){
        long bookid = 121;
        BookStatus bookStatus = bookInputEntity.getBookStatus();
        Mockito.when(this.bookAdapter.updatestatus(bookid, bookStatus))
                .thenThrow(new ResourcenotFoundException(BookModel.class, "bookid",String.valueOf(bookid)));
        ResponseEntity<?> response = this.bookController.updatebookstatus(bookid, bookStatus);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    @DisplayName("TestbookbyId should return bookModel")
    void testgetbookbyId(){
        Mockito.when(this.bookAdapter.getbookbyId(id)).thenReturn(bookModel);
        ResponseEntity<BookModel> response = this.bookController.getbookbyId(id);
        Assertions.assertEquals(bookModel, response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}

