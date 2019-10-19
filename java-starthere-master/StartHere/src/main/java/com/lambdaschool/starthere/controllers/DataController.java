package com.lambdaschool.starthere.controllers;

import com.lambdaschool.starthere.models.Book;
import com.lambdaschool.starthere.models.ErrorDetail;
import com.lambdaschool.starthere.models.Section;
import com.lambdaschool.starthere.services.BookService;
import com.lambdaschool.starthere.services.SectionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/data")
public class DataController {

    @Autowired
    private BookService bookService;

    @Autowired
    private SectionService sectionService;

    //   http://localhost:2019/data/books/{id}
    @ApiOperation(value = "Updates book details", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Book updated successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error updating book", response = ErrorDetail.class)
    })
    @PutMapping(value = "/books/{id}")
    public ResponseEntity<?> updateBook(
            @RequestBody
                    Book updateBook,
            @PathVariable
                    long id, HttpServletRequest request) {
        bookService.update(updateBook, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //   http://localhost:2019/data/sections/{id}
    @ApiOperation(value = "Updates section details", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Section updated successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error updating section", response = ErrorDetail.class)
    })
    @PutMapping(value = "/sections/{id}")
    public ResponseEntity<?> updateSection(
            @RequestBody
                    Section updateSection,
            @PathVariable
                    long id, HttpServletRequest request) {
        sectionService.update(updateSection, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //   http://localhost:2019/data/books/{bookid}/authors/{authorid}
    @ApiOperation(value = "Assign a book to an author", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Book assigned Successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error assigning book", response = ErrorDetail.class)
    })
    @PostMapping(value = "/books/{bookid}/authors/{authorid}",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> assignBookToAuthor(@ApiParam(value = "Book Id", required = true, example = "1")
                                                @PathVariable long bookid, @ApiParam(value = "Author Id", required = true, example = "1")
                                                @PathVariable long authorid, HttpServletRequest request) throws URISyntaxException {
        bookService.assignBookToAuthor(bookid, authorid);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    //   http://localhost:2019/data/books/{bookid}
    @ApiOperation(value = "Delete a book", notes = "The book id entered will be deleted", response = void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Book deleted successfully", response = void.class),
            @ApiResponse(code = 500, message = "Error deleting book", response = ErrorDetail.class)
    })
    @DeleteMapping("/books/{bookid}")
    public ResponseEntity<?> deleteBookById(@ApiParam(value = "Book Id", required = true, example = "1")
                                            @PathVariable long bookid, HttpServletRequest request) {
        bookService.delete(bookid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
