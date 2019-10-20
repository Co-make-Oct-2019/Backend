package com.lambdaschool.starthere.repository;

import com.lambdaschool.starthere.models.Useremail;
import com.lambdaschool.starthere.models.Userpost;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserpostRepository extends PagingAndSortingRepository<Userpost, Long> {

    List<Userpost> findAllByUser_Username(String name);

    @Query(value = "SELECT * FROM userposts WHERE userid IS NOT :userid",
            nativeQuery = true)
    List <Userpost> findByNotUserid(long userid);
}
