package com.lambdaschool.starthere.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lambdaschool.starthere.logging.Loggable;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Loggable
@Entity
@Table(name = "userposts",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"userid", "userpostid"})})
public class Userpost extends Auditable{

    @ApiModelProperty(name = "postid", value = "primary key for a post", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userpostid;

    @ApiModelProperty(name = "title", value = "Title of post", example = "There is a pothole on 42nd Street")
    @Column(nullable = false)
    private String title;

    @ApiModelProperty(name = "imageurl", value = "Link to image", example = "http://example.com/img.jpg")
    @Column(nullable = true)
    private String imageurl;

    @ApiModelProperty(name = "line1", value = "First line of post", example = "It is a big pothole")
    @Column(nullable = false)
    private String line1;

    @ManyToOne
    @JoinColumn(name = "userid",
            nullable = false)
    @JsonIgnoreProperties("userposts")
    private User user;


    @ApiModelProperty(name = "zip", value = "Zip code", example = "96021")
    @Column(nullable = false)
    private long zip;
//
//    @ApiModelProperty(name = "votes", value = "Number of votes", example = "12")
//    private long votes;

    public Userpost()
    {

    }

    public Userpost(User user, String title, long zip, String line1, String imageurl) {

        this.user = user;
        this.title = title;
        this.zip = zip;
        this.line1 = line1;
        this.imageurl = imageurl;

    }

    public Userpost(User user, String title, long zip, String line1) {
        this.user = user;
        this.title = title;
        this.zip = zip;
        this.line1 = line1;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getUserpostid() {
        return userpostid;
    }

    public void setUserpostid(long postid) {
        this.userpostid = userpostid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public long getZip() {
        return zip;
    }

    public void setZip(long zip) {
        this.zip = zip;
    }

//    public long getVotes() {
//        return votes;
//    }
//
//    public void setVotes(long votes) {
//        this.votes = votes;
//    }
}