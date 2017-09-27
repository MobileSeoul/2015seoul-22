package com.example.genebe.Model;

import java.util.List;

/**
 * Created by yk on 2015-09-27.
 */
public class Feeds {
    public List<Feed> result;

    public Feeds(List<Feed> feed) {
        this.result = feed;
    }

    public List<Feed> getFeed() {
        return result;
    }

    public void setFeed(List<Feed> feed) {
        this.result = feed;
    }
}
