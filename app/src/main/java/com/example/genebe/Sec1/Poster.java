package com.example.genebe.Sec1;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sumin on 2015-06-23.
 */
public class Poster implements Serializable{
    private int postID;
    private int uploaderID;
    private String uploaderName;
    private String profilePic;
    private String routeReview;
    private ArrayList<String> hashTagList;
    private ArrayList<Integer> storeList;

    public Poster()
    {
    }
    public Poster(int postID, int uploaderID, String uploaderName, String profilePic, String routeReview, ArrayList<String> hashTagList, ArrayList<Integer> storeList)
    {
        this.postID = postID;
        this.uploaderID = uploaderID;
        this.uploaderName = uploaderName;
        this.profilePic = profilePic;
        this.routeReview = routeReview;
        this.hashTagList = hashTagList;
        this.storeList = storeList;
    }
    public void setPostID(int postID)
    {
        this.postID = postID;
    }
    public void setUploaderID(int uploaderID)
    {
        this.uploaderID = uploaderID;
    }
    public void setUploaderName(String uploaderName)
    {
        this.uploaderName = uploaderName;
    }
    public void setProfilePic(String profilePic)
    {
        this.profilePic = profilePic;
    }
    public void setRouteReview(String routeReview)
    {
        this.routeReview = routeReview;
    }
    public void setHashTagList(ArrayList<String> hashTagList)
    {
        this.hashTagList = hashTagList;
    }
    public void setStoreList(ArrayList<Integer> storeList)
    {
        this.storeList = storeList;
    }
    public int getPostID()
    {
        return postID;
    }
    public int getUploaderID()
    {
        return uploaderID;
    }
    public String getUploaderName()
    {
        return uploaderName;
    }
    public String getProfilePic()
    {
        return profilePic;
    }
    public String getRouteReview()
    {
        return routeReview;
    }
    public ArrayList<String> getHashTagList()
    {
        return hashTagList;
    }
    public ArrayList<Integer> getStoreList()
    {
        return storeList;
    }
}
