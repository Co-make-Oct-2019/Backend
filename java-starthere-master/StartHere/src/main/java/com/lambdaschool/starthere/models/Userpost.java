package com.lambdaschool.starthere.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lambdaschool.starthere.logging.Loggable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "Userpost", description = "This model defines how posts are created")
@Loggable
@Entity
@Table(name = "userposts",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"userid", "userpostid"})})

public class Userpost extends Auditable {

//    @ApiModelProperty(name = "postid", value = "primary key for a post", required = true, example = "1")
@ApiModelProperty(hidden=true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userpostid;

    @ApiModelProperty(hidden=true)
    @OneToMany(mappedBy = "userpost",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnoreProperties({"userpost", "postvotes"})
    private List<Postcomment> postcomments = new ArrayList<>();

    @ApiModelProperty(name = "title", value = "This is the title of the post", example = "There is a pothole on 42nd Street", required = true)
    @Column(nullable = false, length = 10_000, columnDefinition = "text")
    @Lob
    @Type(type = "text")
    private String title;

    @ApiModelProperty(name = "imageurl", value = "Link to image", example = "http://example.com/img.jpg")
    @Column(nullable = true)
    private String imageurl;

    @ApiModelProperty(name = "description", value = "The main text body of the post", example = "It is a big pothole", required = true)
    @Column(nullable = false, length = 10_000, columnDefinition = "text")
    @Lob
    @Type(type = "text")
    private String description;

    @ApiModelProperty(hidden=true)
    @ManyToOne
    @JoinColumn(name = "userid",
            nullable = false)
    @JsonIgnoreProperties({"userposts", "userroles", "postvotes"})
    private User user;


    @ApiModelProperty(name = "location", value = "City", example = "Los Angeles", required = true)
    @Column(nullable = false)
    private String location;

//    @ApiModelProperty(name = "count", value = "Current count of votes", required = false, example = "1")
@ApiModelProperty(hidden=true)
    @Column(nullable = false)
    private Integer count;

    @ApiModelProperty(hidden=true)
    @Column(nullable = false)
    private boolean voted;

    @OneToMany(mappedBy = "userpost",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"userpost", "postvotes"})
    @JsonIgnore
    private List<Postvotes> postvotes = new ArrayList<>();

//
//    @ApiModelProperty(name = "votes", value = "Number of votes", example = "12")
//    private long votes;

    public Userpost() {

    }

    public Userpost(User user, String title, String location, String description, String imageurl) {

        this.user = user;
        this.title = title;
        this.location = location;
        this.description = description;
        this.imageurl = imageurl;
        this.count = 0;
        this.voted = false;

    }

    public Userpost(User user, String title, String location, String description) {
        this.user = user;
        this.title = title;
        this.location = location;
        this.description = description;
        this.count = 0;
        this.voted = false;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
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

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Postvotes> getPostvotes() {
        return postvotes;
    }

    public void setPostvotes(List<Postvotes> postvotes) {
        this.postvotes = postvotes;
    }

    //    public long getVotes() {
//        return votes;
//    }
//
//    public void setVotes(long votes) {
//        this.votes = votes;
//    }
}
