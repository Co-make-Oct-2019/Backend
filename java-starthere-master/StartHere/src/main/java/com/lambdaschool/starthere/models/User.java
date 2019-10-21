package com.lambdaschool.starthere.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lambdaschool.starthere.logging.Loggable;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

// User is considered the parent entity

@Loggable
@Entity
@Table(name = "users")
public class User extends Auditable {

    @ApiModelProperty(name = "userid", value = "primary key for a user", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userid;

    @ApiModelProperty(name = "username", value = "username for a user", required = true, example = "johnny22")
    @Column(nullable = false,
            unique = true)
    private String username;

    @ApiModelProperty(name = "userid", value = "primary key for a user", required = false, example = "Seattle")
    @Column(nullable = false)
    private String location;

    @ApiModelProperty(name = "line1", value = "Content line for user profile", required = false, example = "I've lived here for 5 years")
    @Column(nullable = true,
            unique = false)
    private String line1;

    @ApiModelProperty(name = "imageurl", value = "Link to image for user profile", required = false, example = "http://example.com/image.jpg")
    @Column(nullable = true,
            unique = false)
    private String imageurl;

    @ApiModelProperty(name = "password", value = "primary key for a user", required = true, example = "password1234")
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

//    @Column(nullable = false,
//            unique = true)
//    @Email
//    private String primaryemail;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user", "userroles", "userrole"})
    @JsonIgnore
    private List<UserRoles> userroles = new ArrayList<>();


    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user", "postvotes"})
    @JsonIgnore
    private List<Postvotes> postvotes = new ArrayList<>();


    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnoreProperties({"user", "postvotes"})
    private List<Userpost> userposts = new ArrayList<>();

    public User() {
    }

    public User(String username,
                String password,
                List<UserRoles> userRoles, String location) {
        setUsername(username);
        setPassword(password);
        setLocation(location);
        for (UserRoles ur : userRoles) {
            ur.setUser(this);
        }
        this.userroles = userRoles;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        if (username == null) // this is possible when updating a user
        {
            return null;
        } else {
            return username.toLowerCase();
        }
    }

    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public void setPasswordNoEncrypt(String password) {
        this.password = password;
    }

    public List<UserRoles> getUserroles() {
        return userroles;
    }

    public void setUserroles(List<UserRoles> userroles) {
        this.userroles = userroles;
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

    public List<Userpost> getUserposts() {
        return userposts;
    }

    public void setUserposts(List<Userpost> userposts) {
        this.userposts = userposts;
    }

    public List<Postvotes> getPostvotes() {
        return postvotes;
    }

    public void setPostvotes(List<Postvotes> postvotes) {
        this.postvotes = postvotes;
    }

    @JsonIgnore
    public List<SimpleGrantedAuthority> getAuthority() {
        List<SimpleGrantedAuthority> rtnList = new ArrayList<>();

        for (UserRoles r : this.userroles) {
            String myRole = "ROLE_" + r.getRole()
                    .getName()
                    .toUpperCase();
            rtnList.add(new SimpleGrantedAuthority(myRole));
        }

        return rtnList;
    }

    @Override
    public String toString() {
        return "User{" + "userid=" + userid + ", username='" + username + '\'' + ", password='" + password + '\'' + ", userroles=" + userroles + ", useremails=" + '}';
    }
}
