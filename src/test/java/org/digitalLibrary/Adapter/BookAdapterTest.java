package org.digitalLibrary.Adapter;

import org.digitalLibrary.Mappers.Input.BookInputmapper;
import org.digitalLibrary.Services.BookService;
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

import java.awt.print.Book;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class BookAdapterTest {
    @Mock
    private BookService bookService;
    @Mock
    private BookInputmapper bookInputmapper;
    @InjectMocks
    private BookAdapter bookAdapter;

    static private BookInputEntity bookInputEntity;
    static private BookModel bookModel;
    static   private long id;
    static   private List<BookModel> bookModelList;
    @BeforeAll
    static void setup(){
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

        bookModelList = new ArrayList<>();
        bookModelList.add(bookModel);
    }
    @Test
    @DisplayName("Save ")
    void testsaveBook(){
        Mockito.when(this.bookService.addbook(this.bookInputmapper.maptoModel(bookInputEntity))).thenReturn(bookModel);
        BookModel actual = this.bookAdapter.save(bookInputEntity);
        Assertions.assertEquals(actual, bookModel);
    }
    @Test
    @DisplayName("find book with input Id and returns a BookModel")
    void testfindbookbyId(){
        Mockito.when(this.bookService.findBoookById(id)).thenReturn(bookModel);
        BookModel actualoutput = this.bookAdapter.findById(id);
        Assertions.assertEquals(actualoutput, bookModel);
    }
    @Test
    @DisplayName("Update book by ID and return updated BookModel")
    void testUpdateById() {
        Mockito.when(this.bookService.updatebook(this.bookInputmapper.maptoModel(bookInputEntity))).thenReturn(bookModel);
        BookModel actual = this.bookAdapter.updatebook(bookInputEntity);
        Assertions.assertEquals(bookModel, actual);
    }

    @Test
    @DisplayName("Delete book by ID and return deleted BookModel")
    void testDeleteBook() {
        Mockito.when(this.bookService.deletebook(id)).thenReturn(bookModel);
        BookModel actual = this.bookAdapter.deletebook(id);
        Assertions.assertEquals(bookModel, actual);
    }

    @Test
    @DisplayName("Get all books and return list of BookModel")
    void testGetAllBooks() {
        List<BookModel> expectedList = bookModelList;
        Mockito.when(this.bookService.getallbooks()).thenReturn(expectedList);
        List<BookModel> actualList = this.bookAdapter.getallbooks();
        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    @DisplayName("Update book without ID and return updated BookModel")
    void testUpdateBook() {
        Mockito.when(this.bookService.updatebook(this.bookInputmapper.maptoModel(bookInputEntity))).thenReturn(bookModel);
        BookModel actual = this.bookAdapter.updatebook(bookInputEntity);
        Assertions.assertEquals(bookModel, actual);
    }
    @Test
    @DisplayName("upadte status should return bookmodel")
    void testupdatebookstatus(){
        BookStatus bookStatus = bookModel.getBookStatus();
        Mockito.when(bookService.updatebookstatus(id, bookStatus)).thenReturn(bookModel);
        BookModel actual = this.bookAdapter.updatestatus(id, bookStatus);
        Assertions.assertEquals(actual, bookModel);
    }
    @Test
    @DisplayName("upadte status should return bookmodel")
    void testgetbookbyID(){
        Mockito.when(this.bookService.getbookbyid(id)).thenReturn(bookModel);
        BookModel actual = this.bookAdapter.getbookbyId(id);
        Assertions.assertEquals(actual, bookModel);
    }


}
