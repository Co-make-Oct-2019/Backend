package com.lambdaschool.starthere.repository;

import com.lambdaschool.starthere.models.User;

import com.lambdaschool.starthere.models.Userpost;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserpostRepository extends PagingAndSortingRepository<Userpost, Long> {

    List<Userpost> findAllByUser_Username(String name);

//    @Query(value = "SELECT * FROM userposts WHERE location = :location", nativeQuery = true)
    List<Userpost> findAllByLocationContainingIgnoreCase(String location);

    @Query(value = "SELECT * FROM userposts WHERE userid <> :userid",
            nativeQuery = true)
    List <Userpost> findByNotUserid(long userid);

    @Query(value = "SELECT CASE WHEN EXISTS ( SELECT * FROM postvotes  WHERE userpostid = :userpostid AND userid = :userid ) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END", nativeQuery = true)
    boolean checkMatch(long userid, long userpostid);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO postvotes(userpostid, userid) VALUES(:userpostid,:userid)", nativeQuery = true)
    void addVote(long userid, long userpostid);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM postvotes where userpostid = :userpostid and userid = :userid", nativeQuery = true)
    void removeVote(long userid, long userpostid);

    @Query(value = "SELECT COUNT(*) FROM postvotes\n WHERE userpostid = :userpostid", nativeQuery = true)
    int getCount(long userpostid);
}
