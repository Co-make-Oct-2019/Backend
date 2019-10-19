package com.lambdaschool.starthere.services;

import com.lambdaschool.starthere.models.Author;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;


public interface AuthorService {
    ArrayList<Author> findAll(Pageable pageable);

}
