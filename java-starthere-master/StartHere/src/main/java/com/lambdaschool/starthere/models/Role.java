package com.lambdaschool.starthere.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lambdaschool.starthere.logging.Loggable;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Loggable
@Entity
@Table(name = "roles")
public class Role extends Auditable
{
    @ApiModelProperty(name = "roleid", value = "Primary key for a role", required = false, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roleid;

    @ApiModelProperty(name = "name", value = "Name of current role", required = false, example = "admin")
    @Column(nullable = false,
            unique = true)
    private String name;

    @OneToMany(mappedBy = "role",
               cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"role", "userroles"})
    private List<UserRoles> userroles = new ArrayList<>();

    public Role()
    {
    }

    public Role(String name)
    {
        this.name = name.toUpperCase();
    }

    public long getRoleid()
    {
        return roleid;
    }

    public void setRoleid(long roleid)
    {
        this.roleid = roleid;
    }

    public String getName()
    {
        if (name == null)
        {
            return null;
        } else
        {
            return name.toUpperCase();
        }
    }

    public void setName(String name)
    {
        this.name = name.toUpperCase();
    }

    public List<UserRoles> getUserroles()
    {
        return userroles;
    }

    public void setUserroles(List<UserRoles> userroles)
    {
        this.userroles = userroles;
    }

}
