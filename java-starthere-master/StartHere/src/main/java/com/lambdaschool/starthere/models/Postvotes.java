package com.lambdaschool.starthere.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lambdaschool.starthere.logging.Loggable;

import javax.persistence.*;
import java.io.Serializable;

@Loggable
@Entity
@Table(name = "postvotes",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"userid", "userpostid"})})
public class Postvotes extends Auditable implements Serializable {


    @Id
    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties("postvotes")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "userpostid")
    @JsonIgnoreProperties("postvotes")
    private Userpost userpost;

    public Postvotes()
    {

    }

    public Postvotes(User user, Userpost userpost)
    {
        this.user = user;
        this.userpost = userpost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Userpost getUserpost() {
        return userpost;
    }

    public void setUserpost(Userpost userpost) {
        this.userpost = userpost;
    }
}
