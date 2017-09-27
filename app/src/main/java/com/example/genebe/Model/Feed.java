package com.example.genebe.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yk on 2015-09-27.
 */
public class Feed implements Serializable {

    private String _id;
    private int postid;
    private String uploader;
    private String uploaderpic;
    private List<Integer> route;
    private String postname;
    private String review;
    private List<String> hashtag;
    private int like_cnt;
    private String like_pressed;
    private int comment_cnt;
    private List<String> imgs;
    //public int timestamp;

    public Feed() {
        route = new ArrayList<>();
        hashtag = new ArrayList<>();
    }

    public Feed(String _id, int postid, String uploader, String uploaderpic, List<Integer> route, String postname, String review, List<String> hashtag,
                  int like_cnt, int comment_cnt, List<String> imgs, String like_pressed) {
        this._id = _id;
        this.postid = postid;
        this.uploader = uploader;
        this.uploaderpic = uploaderpic;
        this.route = route;
        this.postname = postname;
        this.review = review;
        this.hashtag = hashtag;
        this.like_cnt = like_cnt;
        this.comment_cnt = comment_cnt;
        this.imgs = imgs;
        this.like_pressed = like_pressed;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getUploaderpic() {
        return uploaderpic;
    }

    public void setUploaderpic(String uploaderpic) {
        this.uploaderpic = uploaderpic;
    }

    public List<Integer> getRoute() {
        return route;
    }

    public void setRoute(List<Integer> route) {
        this.route = route;
    }

    public String getPostname() {
        return postname;
    }

    public void setPostname(String postname) {
        this.postname = postname;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public List<String> getHashtag() {
        return hashtag;
    }

    public void setHashtag(List<String> hashtag) {
        this.hashtag = hashtag;
    }

    public int getLike_cnt() {
        return like_cnt;
    }

    public void setLike_cnt(int like_cnt) {
        this.like_cnt = like_cnt;
    }

    public int getComment_cnt() {
        return comment_cnt;
    }

    public void setComment_cnt(int comment_cnt) {
        this.comment_cnt = comment_cnt;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public String getLike_pressed() {
        return like_pressed;
    }

    public void setLike_pressed(String like_pressed) {
        this.like_pressed = like_pressed;
    }
}

