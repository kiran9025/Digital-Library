package org.digitalLibrary.Controller;

import jakarta.validation.Valid;
import org.digitalLibrary.Adapter.BookAdapter;
import org.digitalLibrary.Exceptions.ResourcenotFoundException;
import org.digitalLibrary.entities.input.BookInputEntity;
import org.digitalLibrary.enums.BookStatus;
import org.digitalLibrary.model.BookModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
        private final BookAdapter bookAdapter;

        @Autowired
    public BookController(BookAdapter bookAdapter) {
        this.bookAdapter = bookAdapter;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addbook(@Valid  @RequestBody BookInputEntity bookInputEntity){
        return new ResponseEntity<>(this.bookAdapter.save(bookInputEntity), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> finddByID(@PathVariable long  id){
        try{
            return new ResponseEntity<>(this.bookAdapter.findById(id), HttpStatus.CREATED);
        }catch (ResourcenotFoundException e){
            return new ResponseEntity<>( e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable long id){
            try{
            BookModel bookModel = this.bookAdapter.deletebook(id);
            return ResponseEntity.ok(bookModel);
            }catch (ResourcenotFoundException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            }
    }

    @GetMapping("/getallbooks")
    public List<BookModel>  getallbooks(){
        return this.bookAdapter.getallbooks();
    }
    @PutMapping
    public ResponseEntity<?> updatebook(@RequestBody BookInputEntity bookInputEntity){
            try{
                return new ResponseEntity<>(this.bookAdapter.updatebook(bookInputEntity), HttpStatus.CREATED);
            }catch (ResourcenotFoundException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
    }

    @PutMapping("/{bookid}")
    public ResponseEntity<?> updatebookstatus(@PathVariable long bookid, @RequestParam BookStatus bookStatus){
            try{
                return new ResponseEntity<>(this.bookAdapter.updatestatus(bookid, bookStatus), HttpStatus.CREATED);
            }catch (ResourcenotFoundException e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
    }
    @GetMapping({"/newid/{id}"})
    public ResponseEntity<BookModel> getbookbyId(@PathVariable long id){
            try{
                return new ResponseEntity<>(this.bookAdapter.getbookbyId(id), HttpStatus.OK);
            }catch (ResourcenotFoundException e){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }


}
