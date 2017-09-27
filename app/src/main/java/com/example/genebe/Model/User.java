package com.example.genebe.Model;

/**
 * Created by malgogi on 15. 9. 18..
 */
public class User {
    public int _id;
    public String username;
    public String password;
    public String access_token;
    public String refresh_token;

    public User( int _id, String username, String password, String access_token, String refresh_token ) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
    }
}
