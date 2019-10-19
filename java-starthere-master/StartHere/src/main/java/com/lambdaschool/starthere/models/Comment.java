package com.lambdaschool.starthere.models;

import com.lambdaschool.starthere.logging.Loggable;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Loggable
@Entity
@Table(name = "comment")
public class Comment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long commentid;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String line1;

    @Column(nullable = true)
    private String imageurl;


    public Comment()
    {

    }

    public Comment(String title, String line1, String imageurl) {
        this.title = title;
        this.line1 = line1;
        this.imageurl = imageurl;
    }

    public Comment(String title, String line1) {
        this.title = title;
        this.line1 = line1;
//        this.imageurl = imageurl;
    }

    public long getCommentid() {
        return commentid;
    }

    public void setCommentid(long commentid) {
        this.commentid = commentid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
