package com.example.genebe.card;

import android.graphics.Bitmap;

/**
 * Created by kyungrakpark on 15. 6. 29..
 */
public class UploadSummaryCardInformation {
    public String userID;
    public Bitmap storeimgurl1;
    public Bitmap storeimgurl2;
    public Bitmap storeimgurl3;
    public Bitmap storeimgurl4;
    public String posttitle;
    public String postreview;
    public String hashtag;

    public UploadSummaryCardInformation(String userID, Bitmap storeimgurl1, Bitmap storeimgurl2, Bitmap storeimgurl3, Bitmap storeimgurl4, String posttitle,
                                        String postreview, String hashtag){
        this.userID=userID;
        this.storeimgurl1=storeimgurl1;
        this.storeimgurl2=storeimgurl2;
        this.storeimgurl3=storeimgurl3;
        this.storeimgurl4=storeimgurl4;
        this.posttitle=posttitle;
        this.postreview=postreview;
        this.hashtag=hashtag;
    }
}

