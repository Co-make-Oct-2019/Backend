package com.lambdaschool.starthere.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lambdaschool.starthere.logging.Loggable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@ApiModel(value = "Postcomment", description = "This model defines how comments are created")
@Loggable
@Entity
@Table(name = "postcomments",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"userpostid", "postcommentid"})})
public class Postcomment extends Auditable{

//    @ApiModelProperty(name = "postcommentid", value = "primary key for a comment", required = true, example = "1")
    @ApiModelProperty(hidden=true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long postcommentid;

    @ApiModelProperty(name = "title", value = "Title of post", example = "That's a good point")
    @Column(nullable = false, length = 10_000, columnDefinition = "text")
    @Lob
    @Type(type = "text")
    private String title;

    @ApiModelProperty(name = "imageurl", value = "Link to image", example = "http://example.com/img.jpg")
    @Column(nullable = true)
    private String imageurl;


    @ApiModelProperty(name = "description", value = "First line of comment", example = "You're right, we should do something!")
    @Column(nullable = false, length = 10_000, columnDefinition = "text")
    @Lob
    @Type(type = "text")
    private String description;

    @ApiModelProperty(hidden=true)
    @ManyToOne
    @JoinColumn(name = "userpostid",
            nullable = false)
    @JsonIgnoreProperties("postcomments")
    private Userpost userpost;

    @ApiModelProperty(hidden=true)
    @ManyToOne
    @JoinColumn(name = "userid",
            nullable = false)
    @JsonIgnoreProperties({"postcomments", "userposts", "postvotes"})
    private User user;


    public Postcomment()
    {

    }

    public Postcomment(User user, Userpost userpost, String title, String description, String imageurl) {
        this.user = user;
        this.title = title;
        this.imageurl = imageurl;
        this.description = description;
        this.userpost = userpost;

    }

    public Postcomment(User user, Userpost userpost, String title, String description) {
        this.user = user;
        this.title = title;
        this.description = description;
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

//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }

    public Userpost getUserpost() {
        return userpost;
    }

    public void setUserpost(Userpost userpost) {
        this.userpost = userpost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
