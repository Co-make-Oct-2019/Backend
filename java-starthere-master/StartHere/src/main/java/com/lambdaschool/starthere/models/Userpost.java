package com.lambdaschool.starthere.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lambdaschool.starthere.logging.Loggable;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Loggable
@Entity
@Table(name = "userposts",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"userid", "userpostid"})})

public class Userpost extends Auditable {

    @ApiModelProperty(name = "postid", value = "primary key for a post", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userpostid;

    @OneToMany(mappedBy = "userpost",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnoreProperties({"userpost"})
    private List<Postcomment> postcomments = new ArrayList<>();

    @ApiModelProperty(name = "title", value = "Title of post", example = "There is a pothole on 42nd Street")
    @Column(nullable = false, length = 10_000)
    @Lob
    private String title;

    @ApiModelProperty(name = "imageurl", value = "Link to image", example = "http://example.com/img.jpg")
    @Column(nullable = true)
    private String imageurl;

    @ApiModelProperty(name = "line1", value = "First line of post", example = "It is a big pothole")
    @Column(nullable = false, length = 10_000)
    @Lob
    private String line1;

    @ManyToOne
    @JoinColumn(name = "userid",
            nullable = false)
    @JsonIgnoreProperties({"userposts", "userroles"})
    private User user;


    @ApiModelProperty(name = "location", value = "City", example = "Los Angeles")
    @Column(nullable = false)
    private String location;
//
//    @ApiModelProperty(name = "votes", value = "Number of votes", example = "12")
//    private long votes;

    public Userpost() {

    }

    public Userpost(User user, String title, String location, String line1, String imageurl) {

        this.user = user;
        this.title = title;
        this.location = location;
        this.line1 = line1;
        this.imageurl = imageurl;

    }

    public Userpost(User user, String title, String location, String line1) {
        this.user = user;
        this.title = title;
        this.location = location;
        this.line1 = line1;
    }

    public List<Postcomment> getPostcomments() {
        return postcomments;
    }

    public void setPostcomments(List<Postcomment> postcomments) {
        this.postcomments = postcomments;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

//    public long getVotes() {
//        return votes;
//    }
//
//    public void setVotes(long votes) {
//        this.votes = votes;
//    }
}
