package com.example.genebe.card;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 사하앍 on 2015-05-28.
 */
public class GenebeCardInformation implements Serializable {
    public String writer_id;
    public String route_name;
    public String route_content;
    public ArrayList<String> tags;
    public ArrayList<Integer> shopIds;
    public ArrayList<String> photoIds;
    public int postId;

    public GenebeCardInformation() {
        tags = new ArrayList<>();
        shopIds = new ArrayList<>();
        photoIds = new ArrayList<>();
    }

    public GenebeCardInformation(String writer_id, ArrayList<String> photoIds, String route_name, String route_content, ArrayList<String> tags, ArrayList<Integer> shopIds) {
        this.writer_id = writer_id;
        this.photoIds = photoIds;
        this.route_name = route_name;
        this.route_content = route_content;
        this.tags = tags;
        this.shopIds = shopIds;
    }

    /*
    public GenebeCardInformation(String writer_id, int photoId1, int photoId2, String route_name, String route_content, ArrayList<String> tags) {
        this.writer_id = writer_id;
        this.photoId1 = photoId1;
        this.photoId2 = photoId2;
        this.route_name = route_name;
        this.route_content = route_content;
        this.tags = tags;
    }

    public GenebeCardInformation(String writer_id, int photoId1, int photoId2, int photoId3, String route_name, String route_content, ArrayList<String> tags) {
        this.writer_id = writer_id;
        this.photoId1 = photoId1;
        this.photoId2 = photoId2;
        this.photoId3 = photoId3;
        this.route_name = route_name;
        this.route_content = route_content;
        this.tags = tags;
    }
    */
}

