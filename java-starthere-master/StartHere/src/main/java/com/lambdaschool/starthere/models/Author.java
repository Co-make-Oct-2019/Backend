package com.lambdaschool.starthere.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "author")
public class Author extends Auditable {

    @ApiModelProperty(name = "authorid", value = "Primary key for an author", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long authorid;

    @ApiModelProperty(name = "fname", value = "First name of author", example = "Earl")
    private String fname;

    @ApiModelProperty(name = "lname", value = "First name of author", example = "Nightingale")
    private String lname;

    @OneToMany(mappedBy = "author",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties("author")
    private List<Wrote> wrote = new ArrayList<>();

    public Author() {

    }

    public Author(String fname, String lname) {
        this.fname = fname;
        this.lname = lname;
    }

    public long getAuthorid() {
        return authorid;
    }

    public void setAuthorid(long authorid) {
        this.authorid = authorid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public List<Wrote> getwrote() {
        return wrote;
    }

    public void setwrote(List<Wrote> wrote) {
        this.wrote = wrote;
    }
}
