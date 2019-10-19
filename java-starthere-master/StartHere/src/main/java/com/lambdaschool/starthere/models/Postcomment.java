package com.lambdaschool.starthere.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lambdaschool.starthere.logging.Loggable;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Loggable
@Entity
@Table(name = "postcomments",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"userpostid", "postcommentid"})})
public class Postcomment extends Auditable{

    @ApiModelProperty(name = "postcommentid", value = "primary key for a comment", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long postcommentid;

    @ApiModelProperty(name = "title", value = "Title of post", example = "That's a good point")
    @Column(nullable = false)
    private String title;

    @ApiModelProperty(name = "imageurl", value = "Link to image", example = "http://example.com/img.jpg")
    @Column(nullable = true)
    private String imageurl;

    @ApiModelProperty(name = "line1", value = "First line of comment", example = "You're right, we should do something!")
    @Column(nullable = false)
    private String line1;

    @ManyToOne
    @JoinColumn(name = "userpostid",
            nullable = false)
    @JsonIgnoreProperties("postcomments")
    private Userpost userpost;

    @ManyToOne
    @JoinColumn(name = "userid",
            nullable = false)
    @JsonIgnoreProperties("postcomments")
    private User user;


    public Postcomment()
    {

    }

    public Postcomment(User user, Userpost userpost, String title, String line1, String imageurl) {
        this.user = user;
        this.title = title;
        this.imageurl = imageurl;
        this.line1 = line1;
        this.userpost = userpost;
    }

    public Postcomment(User user, Userpost userpost, String title, String line1) {
        this.user = user;
        this.title = title;
        this.line1 = line1;
        this.userpost = userpost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getPostcommentid() {
        return postcommentid;
    }

    public void setPostcommentid(long postcommentid) {
        this.postcommentid = postcommentid;
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

    public Userpost getUserpost() {
        return userpost;
    }

    public void setUserpost(Userpost userpost) {
        this.userpost = userpost;
    }
}
