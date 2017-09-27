package com.example.genebe.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yk on 2015-10-31.
 */
public class Comments implements Serializable {

    private List<Comment> comment;

    public Comments (List<Comment> comment) {
        this.comment = comment;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }
}
