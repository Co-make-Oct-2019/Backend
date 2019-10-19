package com.lambdaschool.starthere.models;

import com.lambdaschool.starthere.logging.Loggable;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Loggable
@Entity
@Table(name = "post")
public class Post extends Auditable{

    @ApiModelProperty(name = "postid", value = "primary key for a post", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long postid;

    @ApiModelProperty(name = "title", value = "Title of post", example = "There is a pothole on 42nd Street")
    @Column(nullable = false)
    private String title;

    @ApiModelProperty(name = "imageurl", value = "Link to image", example = "http://example.com/img.jpg")
    @Column(nullable = true)
    private String imageurl;

    @ApiModelProperty(name = "line1", value = "First line of post", example = "It is a big pothole")
    @Column(nullable = false)
    private String line1;

//    @ApiModelProperty(name = "line2", value = "Second line of post", example = "Cars keep hitting the pothole")
//    @Column(nullable = true)
//    private String line2;
//
//    @ApiModelProperty(name = "line3", value = "Third line of post", example = "It is a deep pothole")
//    @Column(nullable = true)
//    private String line3;
//
//    @ApiModelProperty(name = "line4", value = "Fourth line of post", example = "Someone please fix that pothole")
//    @Column(nullable = true)
//    private String line4;

    @ApiModelProperty(name = "zip", value = "Zip code", example = "96021")
    @Column(nullable = false)
    private long zip;

    @ApiModelProperty(name = "votes", value = "Number of votes", example = "12")
//    @Column(nullable = false)
    private long votes;

    public Post()
    {

    }

    public Post(String title, long zip, String line1, String imageurl) {
        this.title = title;
        this.zip = zip;
        this.line1 = line1;
        this.imageurl = imageurl;

    }

    public Post(String title, long zip, String line1) {
        this.title = title;
        this.zip = zip;
        this.line1 = line1;
    }

    public long getPostid() {
        return postid;
    }

    public void setPostid(long postid) {
        this.postid = postid;
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

    public long getVotes() {
        return votes;
    }

    public void setVotes(long votes) {
        this.votes = votes;
    }
}
