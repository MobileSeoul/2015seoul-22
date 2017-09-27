package com.example.genebe.upload;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.genebe.Sec1.Store;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by kyungrakpark on 15. 6. 23..
 */
public class UploadConstants {

    public static ArrayList<Store> storesUploadSelectionFrag;

    public static JSONArray searchresultarray;
    public static ArrayList<LatLng> searchresultlatlngarray;
    public static LatLng uploadcompleteroute[];

    public static int currentworkingfrag;
    public static int selectedstorenumbers;
    public static int store_like_temp;

    public static boolean frag0_isfirst;
    public static boolean frag0_isPic;

    public static String frag0_shopid="0";
    public static String frag0_shopname;
    public static String frag0_shopaddr;
    public static String frag0_shopphone;

    public static double frag0_lat;
    public static double frag0_lng;
    public static LatLng frag0_latlng;

    public static Bitmap frag0_img;
    public static int frag0_store_like;
    public static String frag0_store_review;


    public static boolean frag1_isfirst;
    public static boolean frag1_isPic;

    public static String frag1_shopid="0";
    public static String frag1_shopname;
    public static String frag1_shopaddr;
    public static String frag1_shopphone;

    public static double frag1_lat;
    public static double frag1_lng;
    public static LatLng frag1_latlng;

    public static Bitmap frag1_img;
    public static int frag1_store_like;
    public static String frag1_store_review;



    public static boolean frag2_isfirst;
    public static boolean frag2_isPic;

    public static String frag2_shopid="0";
    public static String frag2_shopname;
    public static String frag2_shopaddr;
    public static String frag2_shopphone;

    public static double frag2_lat;
    public static double frag2_lng;
    public static LatLng frag2_latlng;

    public static Bitmap frag2_img;
    public static int frag2_store_like;
    public static String frag2_store_review;


    public static boolean frag3_isfirst;
    public static boolean frag3_isPic;

    public static String frag3_shopid="0";
    public static String frag3_shopname;
    public static String frag3_shopaddr;
    public static String frag3_shopphone;

    public static double frag3_lat;
    public static double frag3_lng;
    public static LatLng frag3_latlng;

    public static Bitmap frag3_img;
    public static int frag3_store_like;
    public static String frag3_store_review;


    public void setUploadStoreFrag0(String newUploadstore){
        frag0_shopname = newUploadstore;
    }

    public void setUploadStoreFrag1(String newUploadstore){
        frag1_shopname = newUploadstore;
    }

    public void setUploadStoreFrag2(String newUploadstore){
        frag2_shopname = newUploadstore;
    }

    public void setUploadStoreFrag3(String newUploadstore){
        frag3_shopname = newUploadstore;
    }

    public void setIsFirstFrag0(boolean newisFirst){
        frag0_isfirst=newisFirst;
    }

    public void setIsFirstFrag1(boolean newisFirst){
        frag1_isfirst=newisFirst;
    }

    public void setIsFirstFrag2(boolean newisFirst){
        frag2_isfirst=newisFirst;
    }

    public void setIsFirstFrag3(boolean newisFirst){
        frag3_isfirst=newisFirst;
    }

    public void setIsPicFrag0(boolean newisPic){
        frag0_isPic=newisPic;
    }

    public void setIsPicFrag1(boolean newisPic){
        frag1_isPic=newisPic;
    }

    public void setIsPicFrag2(boolean newisPic){
        frag2_isPic=newisPic;
    }

    public void setIsPicFrag3(boolean newisPic){
        frag3_isPic=newisPic;
    }

    //Overloading 방식
    public void setlatlngFrag0(LatLng newlatlng){
        frag0_lat=newlatlng.latitude;
        frag0_lng=newlatlng.longitude;
        frag0_latlng=newlatlng;
    }

    public void setlatlngFrag0(double newlat, double newlng){
        frag0_lat=newlat;
        frag0_lng=newlng;
        frag0_latlng=new LatLng(newlat,newlng);
    }

    public void setlatlngFrag1(LatLng newlatlng){
        frag1_lat=newlatlng.latitude;
        frag1_lng=newlatlng.longitude;
        frag1_latlng=newlatlng;
    }

    public void setlatlngFrag1(double newlat, double newlng){
        frag1_lat=newlat;
        frag1_lng=newlng;
        frag1_latlng=new LatLng(newlat,newlng);
    }

    public void setlatlngFrag2(LatLng newlatlng){
        frag2_lat=newlatlng.latitude;
        frag2_lng=newlatlng.longitude;
        frag2_latlng=newlatlng;
    }

    public void setlatlngFrag2(double newlat, double newlng){
        frag2_lat=newlat;
        frag2_lng=newlng;
        frag2_latlng=new LatLng(newlat,newlng);
    }

    public void setlatlngFrag3(LatLng newlatlng){
        frag3_lat=newlatlng.latitude;
        frag3_lng=newlatlng.longitude;
        frag3_latlng=newlatlng;
    }

    public void setlatlngFrag3(double newlat, double newlng){
        frag3_lat=newlat;
        frag3_lng=newlng;
        frag3_latlng=new LatLng(newlat,newlng);
    }

    public static int getselectedstorenumbers(){
        selectedstorenumbers=0;
        if(frag0_isfirst==false){
            selectedstorenumbers++;
        }
        if(frag1_isfirst==false){
            selectedstorenumbers++;
        }
        if(frag2_isfirst==false){
            selectedstorenumbers++;
        }
        if(frag3_isfirst==false){
            selectedstorenumbers++;
        }

        return selectedstorenumbers;
    }

    public void initialize(){

        selectedstorenumbers=0;

        frag0_shopname="01";
        frag1_shopname="02";
        frag2_shopname="03";
        frag3_shopname="04";

        frag0_isfirst=true;
        frag1_isfirst=true;
        frag2_isfirst=true;
        frag3_isfirst=true;

        setlatlngFrag0(0, 0);
        setlatlngFrag1(0, 0);
        setlatlngFrag2(0, 0);
        setlatlngFrag3(0, 0);

    }

    public static void saveCompleteRouteLatLng(){

        if(frag3_lat!=0){
            uploadcompleteroute= new LatLng[]{frag0_latlng, frag1_latlng, frag2_latlng, frag3_latlng};
        }else if(frag2_lat!=0){
            uploadcompleteroute= new LatLng[]{frag0_latlng, frag1_latlng, frag2_latlng};
        }else if(frag1_lat!=0){
            uploadcompleteroute= new LatLng[]{frag0_latlng, frag1_latlng};
        }else if(frag0_lat!=0){
            uploadcompleteroute= new LatLng[]{frag0_latlng};
        }
    }

    public void saveSelectedSearchStore(int fragment_position, int position){

        String shopid="";
        String name="";
        String old_addr="";
        String phone="";
        double lat = 0;
        double lng = 0;

        try {
            shopid=setEncoding(searchresultarray.getJSONObject(position).getString("shopid"));
            name=setEncoding(searchresultarray.getJSONObject(position).getString("name"));
            old_addr=setEncoding(searchresultarray.getJSONObject(position).getString("old_addr"));
            phone=setEncoding(searchresultarray.getJSONObject(position).getString("phone"));
            lat=Double.parseDouble(setEncoding(searchresultarray.getJSONObject(position).getString("lat")));
            lng=Double.parseDouble(setEncoding(searchresultarray.getJSONObject(position).getString("lng")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        switch(fragment_position){
            case 0:
                frag0_shopid=shopid;
                frag0_shopname=name;
                frag0_shopaddr=old_addr;
                frag0_shopphone=phone;
                setlatlngFrag0(lat, lng);
                break;
            case 1:
                frag1_shopid=shopid;
                frag1_shopname=name;
                frag1_shopaddr=old_addr;
                frag1_shopphone=phone;
                setlatlngFrag1(lat, lng);
                break;
            case 2:
                frag2_shopid=shopid;
                frag2_shopname=name;
                frag2_shopaddr=old_addr;
                frag2_shopphone=phone;
                setlatlngFrag2(lat, lng);
                break;
            case 3:
                frag3_shopid=shopid;
                frag3_shopname=name;
                frag3_shopaddr=old_addr;
                frag3_shopphone=phone;
                setlatlngFrag3(lat, lng);
                break;

        }

    }

    public static String setEncoding(String beforeencoding){
        String encodedstring="";

        try {
            encodedstring=new String(beforeencoding.getBytes("8859_1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encodedstring;
    }

    public static String getShopName(int storecount){
        //입력 받은 가게 숫자에 맞는 가게 이름을 반환하는 메소드
        //UploadDrawRouteActivity에서 경로 마커 이름을 넣기 위해 생성

        switch(storecount) {
            case (0):
                return frag0_shopname;
            case (1):
                return frag1_shopname;
            case (2):
                return frag2_shopname;
            case (3):
                return frag3_shopname;
            default:
                return "매칭되는 것이 없습니다";
        }
    }

}
