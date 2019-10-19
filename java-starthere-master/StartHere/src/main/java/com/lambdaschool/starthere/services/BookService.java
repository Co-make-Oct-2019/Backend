package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.Book;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

public interface BookService {

    ArrayList<Book> findAll(Pageable pageable);

    Book update(Book student, long id);

    void assignBookToAuthor(long bookid, long authorid);

    void delete(long id);
}
