package com.lambdaschool.starthere.models;

import com.lambdaschool.starthere.logging.Loggable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Loggable
@Entity
@Table(name = "postcomments",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"postid", "postcommentid"})})
public class Postcomment {
}
