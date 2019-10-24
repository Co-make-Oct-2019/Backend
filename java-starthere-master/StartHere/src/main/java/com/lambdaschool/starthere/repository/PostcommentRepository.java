package com.lambdaschool.starthere.repository;

import com.lambdaschool.starthere.models.Postcomment;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostcommentRepository extends PagingAndSortingRepository<Postcomment, Long> {

    List<Postcomment> findAllByUser_Username(String name);

}
