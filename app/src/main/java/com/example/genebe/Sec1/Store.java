package com.example.genebe.Sec1;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by sumin on 2015-06-22.
 */
public class Store {
    public int shopID;
    public int postID;
    public String storeName;
    public String address;
    public String telephone;
    public LatLng latLng;
    public ArrayList<String> photoList;
    public int imgCount;
    public String storeReview;
    public int storeRate;

    public Store()
    {

        photoList = new ArrayList<>();
    }
    public Store(int shopID, int postID, String storeName, String address, String telephone, LatLng position, ArrayList<String> photoList, int imgCount, String storeReview, int storeRate)
    {
        this.shopID = shopID;
        this.postID = postID;
        this.storeName = storeName;
        this.address = address;
        this.telephone = telephone;
        this.latLng = position;
        this.photoList = photoList;
        this.imgCount = imgCount;
        this.storeReview = storeReview;
        this.storeRate = storeRate;
    }
    public void setShopID(int shopID)
    {
        this.shopID = shopID;
    }
    public void setPostID(int postID)
    {
        this.postID = postID;
    }
    public void setStoreName(String storeName)
    {
        this.storeName = storeName;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }
    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }
    public void setPosition(LatLng position)
    {
        this.latLng = position;
    }
    public void setPhotoList(ArrayList<String> photoList)
    {
        this.photoList = photoList;
    }
    public void setImgCount(int imgCount)
    {
        this.imgCount = imgCount;
    }
    public void setStoreReview(String storeReview)
    {
        this.storeReview = storeReview;
    }
    public void setStoreRate(int storeRate)
    {
        this.storeRate = storeRate;
    }
    public int getShopID()
    {
        return this.shopID;
    }
    public int getPostID()
    {
        return this.postID;
    }
    public String getStoreName()
    {
        return this.storeName;
    }
    public String getAddress()
    {
        return this.address;
    }
    public String getTelephone()
    {
        return this.telephone;
    }
    public LatLng getPosition()
    {
        return this.latLng;
    }
    public ArrayList<String> getPhotoList()
    {
        return this.photoList;
    }
    public int getImgCount()
    {
        return this.imgCount;
    }
    public String getStoreReview()
    {
        return this.storeReview;
    }
    public int getStoreRate()
    {
        return this.storeRate;
    }
}
