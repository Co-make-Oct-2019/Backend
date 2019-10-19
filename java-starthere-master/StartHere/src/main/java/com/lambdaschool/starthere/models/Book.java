package com.lambdaschool.starthere.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lambdaschool.starthere.logging.Loggable;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Loggable
@Entity
@Table(name = "book")
public class Book extends Auditable {

    @ApiModelProperty(name = "bookid", value = "primary key for a book", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bookid;

    @ApiModelProperty(name = "title", value = "Title of Book", example = "The War of Art")
    @Column(nullable = false)
    private String title;

    @ApiModelProperty(name = "ISBN", value = "ISBN Number", example = "978-3-16-148410-0")
    @Column(nullable = false, unique = true)
    private String ISBN;

    @ApiModelProperty(name = "copy", value = "Year the book was published", example = "2011")
    private int copy;

    @OneToMany(mappedBy = "book",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties("book")
    private List<Wrote> wrote = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "sectionid")
    @JsonIgnoreProperties("books")
    private Section section;

    public Book() {

    }

    public Book(String title, String ISBN, Integer copy, Section section) {
        this.title = title;
        this.ISBN = ISBN;
        this.copy = copy;
        this.section = section;
    }

    public long getBookid() {
        return bookid;
    }

    public void setBookid(long bookid) {
        this.bookid = bookid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getCopy() {
        return copy;
    }

    public void setCopy(Integer copy) {
        this.copy = copy;
    }

    public List<Wrote> getwrote() {
        return wrote;
    }

    public void setwrote(List<Wrote> wrote) {
        this.wrote = wrote;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

}
