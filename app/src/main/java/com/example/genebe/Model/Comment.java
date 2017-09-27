package com.example.genebe.Model;

import java.io.Serializable;

/**
 * Created by yk on 2015-10-31.
 */
public class Comment implements Serializable {

    private int postid;
    private int userid;
    private String time;
    private int like_cnt;
    private String _id;
    private String comment;
    private String username;
    private String userpic;

    public Comment() {}

    public Comment(int postid, int userid, String time, int like_cnt, String _id, String comment, String username, String userpic) {
        this.postid = postid;
        this.userid = userid;
        this. time = time;
        this.like_cnt = like_cnt;
        this._id = _id;
        this.comment = comment;
        this.username = username;
        this.userpic = userpic;
    }

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLike_cnt() {
        return like_cnt;
    }

    public void setLike_cnt(int like_cnt) {
        this.like_cnt = like_cnt;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }
}
