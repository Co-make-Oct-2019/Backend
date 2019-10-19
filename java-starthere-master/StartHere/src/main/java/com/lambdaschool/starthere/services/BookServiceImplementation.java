package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.exceptions.ResourceNotFoundException;
import com.lambdaschool.starthere.models.Author;
import com.lambdaschool.starthere.models.Book;
import com.lambdaschool.starthere.models.Wrote;
import com.lambdaschool.starthere.repository.AuthorRepository;
import com.lambdaschool.starthere.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service(value = "bookService")
public class BookServiceImplementation implements BookService{

    @Autowired
    private BookRepository bookRepos;

    @Autowired
    private AuthorRepository authorRepos;

    @Override
    public ArrayList<Book> findAll(Pageable pageable) {
        ArrayList<Book> list = new ArrayList<>();
        bookRepos.findAll(pageable).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Book update(Book book, long id) {
        Book currentBook = bookRepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));

        if (book.getTitle() != null)
        {
            currentBook.setTitle(book.getTitle());
        }
        if (book.getCopy() != 0)
        {
            currentBook.setCopy(book.getCopy());
        }
        if (book.getISBN() != null)
        {
            currentBook.setISBN(book.getISBN());
        }

        return bookRepos.save(currentBook);
    }

    @Override
    public void assignBookToAuthor(long bookid, long authorid) {
        Book currentBook = bookRepos.findById(bookid)
                .orElseThrow(() -> new ResourceNotFoundException(Long.toString(bookid)));

        Author currentAuthor = authorRepos.findById(authorid)
                .orElseThrow(() -> new ResourceNotFoundException(Long.toString(authorid)));

        for(Wrote w: currentAuthor.getwrote())
        {
            if(w.getBook().getBookid() == bookid)
            {
                throw new ResourceNotFoundException("Book already assigned to author");
            }
        }
        bookRepos.assignBookToAuthor(bookid, authorid);
    }

    @Override
    public void delete(long id) {
        if (bookRepos.findById(id).isPresent())
        {
            bookRepos.deleteById(id);
        } else
        {
            throw new ResourceNotFoundException(Long.toString(id));
        }
    }
}
