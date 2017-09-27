package com.example.genebe.Sec4;

/**
 * Created by 사하앍 on 2015-06-03.
 */
public class Sec4ThemeCollection {

    String card_title;
    int main_thumbnail_id;
    int sub_thumbnail1_id;
    int sub_thumbnail2_id;
    int sub_thumbnail3_id;
    boolean isBoardAddButton;

    public Sec4ThemeCollection(String card_title, int main_thumbnail_id, int sub_thumbnail1_id, int sub_thumbnail2_id, int sub_thumbnail3_id, boolean isBoardAddButton ) {
        this.card_title = card_title;
        this.main_thumbnail_id = main_thumbnail_id;
        this.sub_thumbnail1_id = sub_thumbnail1_id;
        this.sub_thumbnail2_id = sub_thumbnail2_id;
        this.sub_thumbnail3_id = sub_thumbnail3_id;
        this.isBoardAddButton = isBoardAddButton;
    }
}
