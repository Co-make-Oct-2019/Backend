package com.lambdaschool.starthere.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lambdaschool.starthere.logging.Loggable;
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
public class User extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userid;

    @Column(nullable = false,
            unique = true)
    private String username;

    @Column(nullable = false)
    private String location;

    @Column(nullable = true,
            unique = false)
    private String line1;

    @Column(nullable = true,
            unique = false)
    private String imageurl;

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
    private List<UserRoles> userroles = new ArrayList<>();

//    @OneToMany(mappedBy = "user",
//               cascade = CascadeType.ALL,
//               orphanRemoval = true)
//    @JsonIgnoreProperties("user")
//    private List<Useremail> useremails = new ArrayList<>();

        @OneToMany(mappedBy = "user",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    @JsonIgnoreProperties("user")
    private List<Userpost> userposts = new ArrayList<>();

    public User()
    {
    }

    public User(String username,
                String password,
//                String primaryemail,
                List<UserRoles> userRoles, String location)
    {
        setUsername(username);
        setPassword(password);
        setLocation(location);
        for (UserRoles ur : userRoles)
        {
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

    public long getUserid()
    {
        return userid;
    }

    public void setUserid(long userid)
    {
        this.userid = userid;
    }

    public String getUsername()
    {
        if (username == null) // this is possible when updating a user
        {
            return null;
        } else
        {
            return username.toLowerCase();
        }
    }

    public void setUsername(String username)
    {
        this.username = username.toLowerCase();
    }

//    public String getPrimaryemail()
//    {
//        if (primaryemail == null) // this is possible when updating a user
//        {
//            return null;
//        } else
//        {
//            return primaryemail.toLowerCase();
//        }
//    }

//    public void setPrimaryemail(String primaryemail)
//    {
//        this.primaryemail = primaryemail.toLowerCase();
//    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public void setPasswordNoEncrypt(String password)
    {
        this.password = password;
    }

    public List<UserRoles> getUserroles()
    {
        return userroles;
    }

    public void setUserroles(List<UserRoles> userroles)
    {
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

    //    public List<Useremail> getUseremails()
//    {
//        return useremails;
//    }
//
//    public void setUseremails(List<Useremail> useremails)
//    {
//        this.useremails = useremails;
//    }

    @JsonIgnore
    public List<SimpleGrantedAuthority> getAuthority()
    {
        List<SimpleGrantedAuthority> rtnList = new ArrayList<>();

        for (UserRoles r : this.userroles)
        {
            String myRole = "ROLE_" + r.getRole()
                                       .getName()
                                       .toUpperCase();
            rtnList.add(new SimpleGrantedAuthority(myRole));
        }

        return rtnList;
    }

    @Override
    public String toString()
    {
        return "User{" + "userid=" + userid + ", username='" + username + '\'' + ", password='" + password + '\'' + ", userroles=" + userroles + ", useremails=" + '}';
    }
    //removed email variables from toString
}
