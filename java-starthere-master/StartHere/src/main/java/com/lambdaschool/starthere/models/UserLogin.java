package com.lambdaschool.starthere.models;

import com.lambdaschool.starthere.logging.Loggable;
import io.swagger.annotations.ApiModelProperty;

@Loggable
public class UserLogin
{
    @ApiModelProperty(name = "username", value = "Username to log in", required = true, example = "johnny22")
    private String username;

    @ApiModelProperty(name = "password", value = "Password to log in", required = true, example = "password1234")
    private String password;


    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
